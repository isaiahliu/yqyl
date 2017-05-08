package org.trinity.yqyl.process.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IContentProcessController;
import org.trinity.yqyl.process.controller.base.IServiceInfoProcessController;
import org.trinity.yqyl.process.controller.base.ISystemAttributeProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceCategoryRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoStasticRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;

@Service
public class ServiceInfoProcessController extends AbstractAutowiredCrudProcessController<ServiceInfo, ServiceInfoDto, ServiceInfoSearchingDto, IServiceInfoRepository>
		implements IServiceInfoProcessController {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	private IServiceSupplierClientRepository serviceSupplierClientRepository;

	@Autowired
	private IServiceInfoStasticRepository serviceInfoStasticRepository;

	@Autowired
	private ISystemAttributeProcessController systemAttributeProcessController;

	@Autowired
	private IContentProcessController contentProcessController;

	@Override
	@Transactional(rollbackOn = IException.class)
	public List<ServiceInfoDto> addAll(final List<ServiceInfoDto> data) throws IException {
		for (final ServiceInfoDto dto : data) {
			final ServiceInfo entity = getDomainObjectConverter().convertBack(dto);
			final ServiceSupplierClient serviceSupplierClient = userRepository.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername()).getServiceSupplierClient();

			if (dto.getServiceSupplierClient() != null && dto.getServiceSupplierClient().getId() != null && dto.getServiceSupplierClient().getId() > 0
					&& dto.getServiceSupplierClient().getId() != serviceSupplierClient.getUserId()) {
				getSecurityUtil().checkAccessRight(AccessRight.OPERATOR);

				entity.setServiceSupplierClient(serviceSupplierClientRepository.findOne(dto.getServiceSupplierClient().getId()));
			} else {
				entity.setServiceSupplierClient(serviceSupplierClient);
			}

			if (dto.getServiceCategory() != null && dto.getServiceCategory().getId() != null && dto.getServiceCategory().getId() > 0) {
				entity.setServiceCategory(serviceCategoryRepository.findOne(dto.getServiceCategory().getId()));
			}

			getDomainEntityRepository().save(entity);

			final ServiceInfoStastic serviceInfoStastic = new ServiceInfoStastic();
			serviceInfoStastic.setServiceInfoId(entity.getId());
			serviceInfoStastic.setServiceInfo(entity);
			serviceInfoStastic.setAppraiseAvg(5d);
			serviceInfoStastic.setAppraiseCount(0l);
			serviceInfoStastic.setOrderCount(0l);

			serviceInfoStasticRepository.save(serviceInfoStastic);

			getDomainObjectConverter().convert(entity, dto, CopyPolicy.TARGET_IS_NULL);
		}

		return data;
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public String addImage(final Long entityId) throws IException {
		final ServiceInfo serviceInfo = getDomainEntityRepository().findOne(entityId);
		if (!serviceInfo.getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())) {
			throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
		}
		final String value = systemAttributeProcessController.getValue(SystemAttributeKey.MAX_SERVICE_INFO_IMAGES);
		final int maxSize = Integer.parseInt(value);

		if (serviceInfo.getImages().size() >= maxSize) {
			throw getExceptionFactory().createException(ErrorMessage.EXCEED_ALLOWED_SIZE);
		}

		final String uuid = contentProcessController.create();

		if (serviceInfo.getImages().isEmpty()) {
			serviceInfo.setImage(uuid);
		}

		serviceInfo.getImages().add(uuid);

		getDomainEntityRepository().save(serviceInfo);

		return uuid;
	}

	@Override
	protected boolean canAccessAllStatus() {
		return getSecurityUtil().hasAccessRight(AccessRight.SERVICE_SUPPLIER) || getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
	}

	@Override
	protected boolean canAccessScopeAll() {
		return true;
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public void delete(final Long id) throws IException {
		final ServiceInfo serviceInfo = getDomainEntityRepository().findOne(id);

		final String username = serviceInfo.getServiceSupplierClient().getUser().getUsername();

		if (!username.equals(getSecurityUtil().getCurrentToken().getUsername())) {
			getSecurityUtil().checkAccessRight(AccessRight.OPERATOR);
		}

		serviceInfo.setStatus(ServiceStatus.DISABLED);

		getDomainEntityRepository().save(serviceInfo);
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public void deleteImage(final Long entityId, final String uuid) throws IException {
		final ServiceInfo serviceInfo = getDomainEntityRepository().findOne(entityId);
		if (!serviceInfo.getServiceSupplierClient().getUser().getUsername().equals(getCurrentUsername())) {
			throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
		}

		serviceInfo.getImages().remove(uuid);

		if (uuid.equals(serviceInfo.getImage())) {
			if (serviceInfo.getImages().isEmpty()) {
				serviceInfo.setImage(null);
			} else {
				serviceInfo.setImage(serviceInfo.getImages().get(0));
			}
		}

		getDomainEntityRepository().save(serviceInfo);
	}

	@Override
	protected void updateRelationshipFields(final ServiceInfo entity, final ServiceInfoDto dto) {
		if (dto.getServiceCategory() != null && dto.getServiceCategory().getId() != null && dto.getServiceCategory().getId() > 0) {
			entity.setServiceCategory(serviceCategoryRepository.findOne(dto.getServiceCategory().getId()));
		}

		if (dto.getProvince() == null) {
			entity.setProvince(null);
		}

		if (dto.getCity() == null) {
			entity.setCity(null);
		}
	}
}

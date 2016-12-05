package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceInfoProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IContentRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceCategoryRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoStasticRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.Content;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceInfo_;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrder_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.User_;

@Service
public class ServiceInfoProcessController
		extends AbstractAutowiredCrudProcessController<ServiceInfo, ServiceInfoDto, ServiceInfoSearchingDto, IServiceInfoRepository>
		implements IServiceInfoProcessController {

	@Autowired
	private IObjectConverter<ServiceCategory, ServiceCategoryDto> serviceCategoryConverter;

	@Autowired
	private IServiceOrderRepository serviceOrderRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	private IServiceSupplierClientRepository serviceSupplierClientRepository;

	@Autowired
	private IServiceInfoStasticRepository serviceInfoStasticRepository;

	@Autowired
	private IContentRepository contentRepository;

	@Override
	@Transactional(rollbackOn = IException.class)
	public List<ServiceInfoDto> addAll(final List<ServiceInfoDto> data) throws IException {
		for (final ServiceInfoDto dto : data) {
			final ServiceInfo entity = getDomainObjectConverter().convertBack(dto);
			final ServiceSupplierClient serviceSupplierClient = userRepository
					.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername()).getServiceSupplierClient();

			if (dto.getServiceSupplierClient() != null && dto.getServiceSupplierClient().getId() != null
					&& dto.getServiceSupplierClient().getId() > 0
					&& dto.getServiceSupplierClient().getId() != serviceSupplierClient.getUserId()) {
				getSecurityUtil().checkAccessRight(AccessRight.OPERATOR);

				entity.setServiceSupplierClient(serviceSupplierClientRepository.findOne(dto.getServiceSupplierClient().getId()));
			} else {
				entity.setServiceSupplierClient(serviceSupplierClient);
			}

			final Content content = new Content();
			content.setContent(new byte[0]);
			content.setStatus(RecordStatus.ACTIVE);
			content.setUuid(UUID.randomUUID().toString());
			contentRepository.save(content);

			entity.setImage(content.getUuid());

			if (dto.getServiceCategory() != null && dto.getServiceCategory().getId() != null && dto.getServiceCategory().getId() > 0) {
				entity.setServiceCategory(serviceCategoryRepository.findOne(dto.getServiceCategory().getId()));
			}

			getDomainEntityRepository().save(entity);

			final ServiceInfoStastic serviceInfoStastic = new ServiceInfoStastic();
			serviceInfoStastic.setServiceInfoId(entity.getId());
			serviceInfoStastic.setServiceInfo(entity);
			serviceInfoStastic.setAppraiseAvg(0d);
			serviceInfoStastic.setAppraiseCount(0l);
			serviceInfoStastic.setOrderCount(0l);

			serviceInfoStasticRepository.save(serviceInfoStastic);

			getDomainObjectConverter().convert(entity, dto, CopyPolicy.TARGET_IS_NULL);
		}

		return data;
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
	public List<ServiceInfoDto> getMe(final ServiceInfoSearchingDto dto) throws IException {
		final String username = getSecurityUtil().getCurrentToken().getUsername();
		final Specification<ServiceInfo> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(cb.equal(root.join(ServiceInfo_.serviceSupplierClient).join(ServiceSupplierClient_.user).get(User_.username),
					username));

			if (dto.getId() != null) {
				predicates.add(cb.equal(root.get(ServiceInfo_.id), dto.getId()));
			}

			if (dto.getName() != null) {
				predicates.add(cb.like(root.get(ServiceInfo_.name), "%" + dto.getName() + "%"));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		final Date fromDate = calendar.getTime();

		return getDomainEntityRepository().findAll(specification).stream().map(item -> {
			final ServiceInfoDto result = getDomainObjectConverter().convert(item);

			final Specification<ServiceOrder> appraiseSpecification = (root, query, cb) -> {
				final List<Predicate> predicates = new ArrayList<>();

				predicates.add(cb.equal(root.join(ServiceOrder_.serviceInfo).get(ServiceInfo_.id), item.getId()));

				predicates.add(cb.greaterThanOrEqualTo(root.get(ServiceOrder_.proposalTime), fromDate));

				return cb.and(predicates.toArray(new Predicate[0]));
			};

			final List<ServiceOrder> orders = serviceOrderRepository.findAll(appraiseSpecification);

			result.setMonthlyProposalOrderCount(orders.size());

			result.setMonthlyRate(orders.stream().map(order -> (order.getAppraise() == null) ? null : order.getAppraise().getAttitudeRate())
					.filter(rate -> rate != null).collect(Collectors.averagingDouble(rate -> rate)));

			final ServiceCategory subCategory = item.getServiceCategory();
			final ServiceCategoryDto subCategoryDto = serviceCategoryConverter.convert(subCategory);
			subCategoryDto.setParent(serviceCategoryConverter.convert(subCategory.getParent()));

			result.setServiceCategory(subCategoryDto);
			return result;
		}).collect(Collectors.toList());
	}

	@Override
	protected boolean canAccessAllStatus() {
		return getSecurityUtil().hasAccessRight(AccessRight.SERVICE_SUPPLIER)
				|| getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
	}

	@Override
	protected boolean canAccessScopeAll() {
		return true;
	}

	@Override
	protected void updateRelationshipFields(final ServiceInfo entity, final ServiceInfoDto dto) {
		if (dto.getServiceCategory() != null && dto.getServiceCategory().getId() != null && dto.getServiceCategory().getId() > 0) {
			entity.setServiceCategory(serviceCategoryRepository.findOne(dto.getServiceCategory().getId()));
		}
	}
}

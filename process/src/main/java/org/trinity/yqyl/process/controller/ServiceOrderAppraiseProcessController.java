package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trinity.common.exception.IException;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderAppraiseProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoStasticRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderAppraiseRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderOperationRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderAppraise;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;

@Service
public class ServiceOrderAppraiseProcessController extends
		AbstractAutowiredCrudProcessController<ServiceOrderAppraise, ServiceOrderAppraiseDto, ServiceOrderAppraiseSearchingDto, IServiceOrderAppraiseRepository>
		implements IServiceOrderAppraiseProcessController {
	@Autowired
	private IServiceOrderRepository serviceOrderRepository;

	@Autowired
	private IServiceSupplierClientRepository serviceSupplierClientRepository;

	@Autowired
	private IServiceOrderOperationRepository serviceOrderOperationRepository;

	@Autowired
	private IServiceInfoStasticRepository serviceInfoStasticRepository;

	@Override
	@Transactional(rollbackOn = IException.class)
	public List<ServiceOrderAppraiseDto> addAll(final List<ServiceOrderAppraiseDto> data) throws IException {
		final Date now = new Date();
		for (final ServiceOrderAppraiseDto dto : data) {
			final ServiceOrder serviceOrder = serviceOrderRepository.findOneByUid(dto.getUid());

			if (!serviceOrder.getUser().getUsername().equals(getSecurityUtil().getCurrentToken().getUsername())) {
				throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
			}

			if (serviceOrder.getStatus() != OrderStatus.AWAITING_APPRAISE) {
				throw getExceptionFactory().createException(ErrorMessage.INCORRECT_SERVICE_ORDER_STATUS);
			}

			final ServiceOrderAppraise serviceOrderAppraise = getDomainObjectConverter().convertBack(dto);
			serviceOrderAppraise.setServiceOrder(serviceOrder);
			serviceOrderAppraise.setServiceOrderId(serviceOrder.getId());
			serviceOrderAppraise.setStatus(RecordStatus.ACTIVE);
			serviceOrder.setSettledTime(now);
			serviceOrder.setStatus(OrderStatus.SETTLED);

			ServiceOrderOperation operation = new ServiceOrderOperation();
			operation.setOperation(OrderOperation.APPRAISED);
			operation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
			operation.setOrderStatus(OrderStatus.SETTLED);
			operation.setStatus(RecordStatus.ACTIVE);
			operation.setServiceOrder(serviceOrder);
			operation.setTimestamp(now);

			serviceOrderOperationRepository.save(operation);

			operation = new ServiceOrderOperation();
			operation.setOperation(OrderOperation.SETTLED);
			operation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
			operation.setOrderStatus(OrderStatus.SETTLED);
			operation.setStatus(RecordStatus.ACTIVE);
			operation.setServiceOrder(serviceOrder);
			operation.setTimestamp(now);

			serviceOrderOperationRepository.save(operation);

			getDomainEntityRepository().save(serviceOrderAppraise);
			serviceOrderRepository.save(serviceOrder);

			if (serviceOrder.getServiceInfo().getServiceInfoStastic() == null) {
				final ServiceInfoStastic serviceInfoStastic = new ServiceInfoStastic();
				serviceInfoStastic.setServiceInfoId(serviceOrder.getServiceInfo().getId());
				serviceInfoStastic.setServiceInfo(serviceOrder.getServiceInfo());
				serviceInfoStastic.setAppraiseAvg(Double.valueOf(serviceOrderAppraise.getStaffRate()));
				serviceInfoStastic.setAppraiseCount(1l);
				serviceInfoStastic.setOrderCount(1l);

				serviceInfoStasticRepository.save(serviceInfoStastic);
			} else {
				final double rate = (serviceOrderAppraise.getStaffRate() + serviceOrderAppraise.getAttitudeRate()
						+ serviceOrderAppraise.getOnTimeRate() + serviceOrderAppraise.getQualityRate()) / 4;
				serviceInfoStasticRepository.updateForNewAppraise(rate, serviceOrder.getServiceInfo().getId());
			}
		}

		return data;
	}

	@Override
	public int countAppraises(final Long serviceSupplierClientId) {
		final ServiceSupplierClient client = serviceSupplierClientRepository.findOne(serviceSupplierClientId);
		if (client == null) {
			return 0;
		}

		return getDomainEntityRepository().countAppraises(client);
	}

	@Override
	public int countAppraisesForRate(final Long serviceSupplierClientId, final int from, final int to) {
		final ServiceSupplierClient client = serviceSupplierClientRepository.findOne(serviceSupplierClientId);
		if (client == null) {
			return 0;
		}

		return getDomainEntityRepository().countAppraisesBetween(client, from, to);
	}

	@Override
	public void reply(final List<ServiceOrderAppraiseDto> data) throws IException {
		final List<ServiceOrderAppraise> entities = data.stream().map(item -> {
			final ServiceOrder serviceOrder = serviceOrderRepository.findOneByUid(item.getUid());
			final ServiceOrderAppraise entity = serviceOrder.getAppraise();

			String username = null;
			try {
				username = getSecurityUtil().getCurrentToken().getUsername();
			} catch (final IException e1) {
				return entity;
			}

			final boolean isSupplier = serviceOrder.getServiceInfo().getServiceSupplierClient().getUser().getUsername().equals(username);
			final boolean isAdmin = getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);

			if (!isSupplier && !isAdmin) {
				return entity;
			}
			entity.setReply(item.getReply());

			final ServiceOrderOperation serviceOrderOperation = new ServiceOrderOperation();

			try {
				serviceOrderOperation.setOperator(getSecurityUtil().getCurrentToken().getUsername());
			} catch (final IException e) {
			}

			serviceOrderOperation.setOperation(OrderOperation.REPLYED);
			serviceOrderOperation.setOrderStatus(OrderStatus.SETTLED);
			serviceOrderOperation.setServiceOrder(serviceOrder);
			serviceOrderOperation.setStatus(RecordStatus.ACTIVE);
			serviceOrderOperation.setTimestamp(new Date());

			serviceOrderOperationRepository.save(serviceOrderOperation);

			return entity;
		}).collect(Collectors.toList());

		getDomainEntityRepository().save(entities);
	}

	@Override
	public void updateAll(final List<ServiceOrderAppraiseDto> data) throws IException {
		final List<ServiceOrderAppraise> entities = new ArrayList<>();
		for (final ServiceOrderAppraiseDto dto : data.stream().filter(item -> !StringUtils.isEmpty(item.getUid()))
				.collect(Collectors.toList())) {
			ServiceOrderAppraise entity = null;

			entity = serviceOrderRepository.findOneByUid(dto.getUid()).getAppraise();
			if (entity == null) {
				throw getExceptionFactory().createException(GeneralErrorMessage.UNABLE_TO_FIND_INSTANCE, dto.getUid());
			}

			entities.add(getDomainObjectConverter().convertBack(dto, entity, CopyPolicy.SOURCE_IS_NOT_NULL));
		}

		getDomainEntityRepository().save(entities);
	}

	@Override
	protected boolean canAccessAllStatus() {
		return getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
	}

	@Override
	protected boolean canAccessScopeAll() {
		return true;
	}
}

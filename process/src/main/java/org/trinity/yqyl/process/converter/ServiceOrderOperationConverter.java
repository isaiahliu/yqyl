package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;

@Component
public class ServiceOrderOperationConverter extends AbstractLookupSupportObjectConverter<ServiceOrderOperation, ServiceOrderOperationDto> {
	private static enum ServiceOrderOperationRelationship {
		NA
	}

	@Autowired
	public ServiceOrderOperationConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceOrderOperationDto source, final ServiceOrderOperation target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);

		copyObject(source::getOperator, target::getOperator, target::setOperator, copyPolicy);
		copyObject(() -> String.join(",", source.getParams()), target::getParams, target::setParams, copyPolicy);
		copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
		copyLookup(source::getOrderStatus, target::getOrderStatus, target::setOrderStatus, OrderStatus.class, copyPolicy);
		copyLookup(source::getOperation, target::getOperation, target::setOperation, OrderOperation.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceOrderOperation source, final ServiceOrderOperationDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);

		copyObject(source::getOperator, target::getOperator, target::setOperator, copyPolicy);
		copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
		copyMessage(source::getOrderStatus, target::getOrderStatus, target::setOrderStatus, copyPolicy);
		copyMessage(source::getOperation, () -> {
			final String params = source.getParams();
			if (params == null) {
				return new String[0];
			} else {
				return params.split(",");
			}
		}, target::getOperation, target::setOperation, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceOrderOperation source, final ServiceOrderOperationDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceOrderOperationRelationship.class)) {
			case NA:
			default:
				break;
		}
	}

	@Override
	protected ServiceOrderOperation createFromInstance() {
		return new ServiceOrderOperation();
	}

	@Override
	protected ServiceOrderOperationDto createToInstance() {
		return new ServiceOrderOperationDto();
	}
}

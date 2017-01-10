package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderAppraise;

@Component
public class ServiceOrderAppraiseConverter extends AbstractLookupSupportObjectConverter<ServiceOrderAppraise, ServiceOrderAppraiseDto> {
	private static enum ServiceOrderAppraiseRelationship {
		SERVICE_ORDER, NA
	}

	@Autowired
	private IObjectConverter<ServiceOrder, ServiceOrderDto> serviceOrderConverter;

	@Autowired
	public ServiceOrderAppraiseConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceOrderAppraiseDto source, final ServiceOrderAppraise target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getAttitudeRate, target::getAttitudeRate, target::setAttitudeRate, copyPolicy);
		copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
		copyObject(source::getReply, target::getReply, target::setReply, copyPolicy);
		copyObject(source::getOnTimeRate, target::getOnTimeRate, target::setOnTimeRate, copyPolicy);
		copyObject(source::getQualityRate, target::getQualityRate, target::setQualityRate, copyPolicy);
		copyObject(source::getStaffRate, target::getStaffRate, target::setStaffRate, copyPolicy);
		copyObject(() -> source.getAttitudeRate() + source.getOnTimeRate() + source.getQualityRate() + source.getStaffRate(),
				target::getTotalRate, target::setTotalRate, copyPolicy);
		copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceOrderAppraise source, final ServiceOrderAppraiseDto target, final CopyPolicy copyPolicy) {
		copyObject(() -> source.getServiceOrder().getUid(), target::getUid, target::setUid, copyPolicy);
		copyObject(source::getAttitudeRate, target::getAttitudeRate, target::setAttitudeRate, copyPolicy);
		copyObject(source::getReply, target::getReply, target::setReply, copyPolicy);
		copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
		copyObject(source::getOnTimeRate, target::getOnTimeRate, target::setOnTimeRate, copyPolicy);
		copyObject(source::getQualityRate, target::getQualityRate, target::setQualityRate, copyPolicy);
		copyObject(source::getStaffRate, target::getStaffRate, target::setStaffRate, copyPolicy);
		copyObject(source::getTotalRate, target::getTotalRate, target::setTotalRate, copyPolicy);

		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceOrderAppraise source, final ServiceOrderAppraiseDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceOrderAppraiseRelationship.class)) {
			case SERVICE_ORDER:
				copyRelationship(source::getServiceOrder, target::setServiceOrder, serviceOrderConverter, relationshipExpression);
				break;
			default:
				break;
		}
	}

	@Override
	protected ServiceOrderAppraise createFromInstance() {
		return new ServiceOrderAppraise();
	}

	@Override
	protected ServiceOrderAppraiseDto createToInstance() {
		return new ServiceOrderAppraiseDto();
	}
}

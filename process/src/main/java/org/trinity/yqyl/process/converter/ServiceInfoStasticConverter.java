package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticDto;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;

@Component
public class ServiceInfoStasticConverter extends AbstractLookupSupportObjectConverter<ServiceInfoStastic, ServiceInfoStasticDto> {
	private static enum ServiceInfoStasticRelationship {
		NA
	}

	@Autowired
	public ServiceInfoStasticConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceInfoStasticDto source, final ServiceInfoStastic target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getServiceInfoId, target::setServiceInfoId, copyPolicy);
		copyObject(source::getAppraiseAvg, target::getAppraiseAvg, target::setAppraiseAvg, copyPolicy);
		copyObject(source::getAppraiseCount, target::getAppraiseCount, target::setAppraiseCount, copyPolicy);
		copyObject(source::getOrderCount, target::getOrderCount, target::setOrderCount, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceInfoStastic source, final ServiceInfoStasticDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getServiceInfoId, target::getId, target::setId, copyPolicy);
		copyObject(source::getAppraiseAvg, target::getAppraiseAvg, target::setAppraiseAvg, copyPolicy);
		copyObject(source::getAppraiseCount, target::getAppraiseCount, target::setAppraiseCount, copyPolicy);
		copyObject(source::getOrderCount, target::getOrderCount, target::setOrderCount, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceInfoStastic source, final ServiceInfoStasticDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceInfoStasticRelationship.class)) {
			case NA:
			default:
				break;
		}
	}

	@Override
	protected ServiceInfoStastic createFromInstance() {
		return new ServiceInfoStastic();
	}

	@Override
	protected ServiceInfoStasticDto createToInstance() {
		return new ServiceInfoStasticDto();
	}
}

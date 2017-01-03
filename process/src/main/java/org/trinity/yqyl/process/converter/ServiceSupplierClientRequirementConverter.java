package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientRequirementDto;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientRequirement;

@Component
public class ServiceSupplierClientRequirementConverter
		extends AbstractLookupSupportObjectConverter<ServiceSupplierClientRequirement, ServiceSupplierClientRequirementDto> {
	private static enum ServiceSupplierClientRequirementRelationship {
		NA
	}

	@Autowired
	public ServiceSupplierClientRequirementConverter(
			final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceSupplierClientRequirementDto source, final ServiceSupplierClientRequirement target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getServiceSupplierClientId, target::setServiceSupplierClientId, copyPolicy);
		copyObject(source::getLastReadTimestamp, target::getLastReadTimestamp, target::setLastReadTimestamp, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceSupplierClientRequirement source, final ServiceSupplierClientRequirementDto target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getServiceSupplierClientId, target::getId, target::setId, copyPolicy);
		copyObject(source::getLastReadTimestamp, target::getLastReadTimestamp, target::setLastReadTimestamp, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceSupplierClientRequirement source,
			final ServiceSupplierClientRequirementDto target, final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceSupplierClientRequirementRelationship.class)) {
			case NA:
			default:
				break;
		}
	}

	@Override
	protected ServiceSupplierClientRequirement createFromInstance() {
		return new ServiceSupplierClientRequirement();
	}

	@Override
	protected ServiceSupplierClientRequirementDto createToInstance() {
		return new ServiceSupplierClientRequirementDto();
	}
}

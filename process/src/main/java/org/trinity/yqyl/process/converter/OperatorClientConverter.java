package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.OperatorClientDto;
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;
import org.trinity.yqyl.repository.business.entity.OperatorClient;

@Component
public class OperatorClientConverter extends AbstractLookupSupportObjectConverter<OperatorClient, OperatorClientDto> {
    private static enum AnnouncementRelationship {
        NA
    }

    @Autowired
    public OperatorClientConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final OperatorClientDto source, final OperatorClient target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getUserId, target::setUserId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, OperatorClientStatus.class, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getStaffNo, target::getStaffNo, target::setStaffNo, copyPolicy);
    }

    @Override
    protected void convertInternal(final OperatorClient source, final OperatorClientDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getUserId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getStaffNo, target::getStaffNo, target::setStaffNo, copyPolicy);
        copyObject(() -> source.getUser().getUsername(), target::getUsername, target::setUsername, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final OperatorClient source, final OperatorClientDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(AnnouncementRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected OperatorClient createFromInstance() {
        return new OperatorClient();
    }

    @Override
    protected OperatorClientDto createToInstance() {
        return new OperatorClientDto();
    }
}

package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.MessageDto;
import org.trinity.yqyl.repository.business.entity.Message;

@Component
public class MessageConverter extends AbstractLookupSupportObjectConverter<Message, MessageDto> {
    private static enum MessageRelationship {
        NA
    }

    @Autowired
    public MessageConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final MessageDto source, final Message target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertInternal(final Message source, final MessageDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final Message source, final MessageDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(MessageRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected Message createFromInstance() {
        return new Message();
    }

    @Override
    protected MessageDto createToInstance() {
        return new MessageDto();
    }
}

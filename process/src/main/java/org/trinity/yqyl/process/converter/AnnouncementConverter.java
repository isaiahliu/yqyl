package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AnnouncementDto;
import org.trinity.yqyl.repository.business.entity.Announcement;

@Component
public class AnnouncementConverter extends AbstractLookupSupportObjectConverter<Announcement, AnnouncementDto> {
    private static enum AnnouncementRelationship {
        NA
    }

    @Autowired
    public AnnouncementConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final AnnouncementDto source, final Announcement target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertInternal(final Announcement source, final AnnouncementDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final Announcement source, final AnnouncementDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(AnnouncementRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected Announcement createFromInstance() {
        return new Announcement();
    }

    @Override
    protected AnnouncementDto createToInstance() {
        return new AnnouncementDto();
    }
}

package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.FavoriteDto;
import org.trinity.yqyl.repository.business.entity.Favorite;

@Component
public class FavoriteConverter extends AbstractLookupSupportObjectConverter<Favorite, FavoriteDto> {
    private static enum FavoriteRelationship {
        NA
    }

    @Autowired
    public FavoriteConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final FavoriteDto source, final Favorite target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertInternal(final Favorite source, final FavoriteDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final Favorite source, final FavoriteDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(FavoriteRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected Favorite createFromInstance() {
        return new Favorite();
    }

    @Override
    protected FavoriteDto createToInstance() {
        return new FavoriteDto();
    }
}

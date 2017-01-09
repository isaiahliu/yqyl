package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;

@Component
public class ServiceCategoryConverter extends AbstractLookupSupportObjectConverter<ServiceCategory, ServiceCategoryDto> {
    private static enum ServiceCategoryRelationship {
        PARENT,
        SERVICE_SUB_CATEGORIES,
        NA
    }

    @Autowired
    public ServiceCategoryConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceCategoryDto source, final ServiceCategory target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
        // copyObject(source::getImage, target::getImage, target::setImage, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceCategory source, final ServiceCategoryDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
        copyObject(source::getImage, target::getImage, target::setImage, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceCategory source, final ServiceCategoryDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceCategoryRelationship.class)) {
        case PARENT:
            copyRelationship(source::getParent, target::setParent, this, relationshipExpression);
            break;
        case SERVICE_SUB_CATEGORIES:
            copyRelationshipList(source::getChildren, target::setServiceSubCategories, this, relationshipExpression);
            break;
        default:
            break;
        }
    }

    @Override
    protected ServiceCategory createFromInstance() {
        return new ServiceCategory();
    }

    @Override
    protected ServiceCategoryDto createToInstance() {
        return new ServiceCategoryDto();
    }
}

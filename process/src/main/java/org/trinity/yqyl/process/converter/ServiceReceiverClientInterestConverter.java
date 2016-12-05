package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientInterest;

@Component
public class ServiceReceiverClientInterestConverter
        extends AbstractLookupSupportObjectConverter<ServiceReceiverClientInterest, ServiceReceiverClientInterestDto> {
    private static enum ServiceReceiverClientInterestRelationship {
        NA
    }

    @Autowired
    public ServiceReceiverClientInterestConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceReceiverClientInterestDto source, final ServiceReceiverClientInterest target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getServiceReceiverClientId, target::setServiceReceiverClientId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);

        copyObject(source::getClub, target::getClub, target::setClub, copyPolicy);
        copyObject(source::getClubActivity, target::getClubActivity, target::setClubActivity, copyPolicy);
        copyObject(source::getClubRole, target::getClubRole, target::setClubRole, copyPolicy);
        copyObject(source::getLiterature, target::getLiterature, target::setLiterature, copyPolicy);
        copyObject(source::getOther, target::getOther, target::setOther, copyPolicy);
        copyObject(source::getOtherInterest, target::getOtherInterest, target::setOtherInterest, copyPolicy);
        copyObject(source::getSport, target::getSport, target::setSport, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceReceiverClientInterest source, final ServiceReceiverClientInterestDto target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getServiceReceiverClientId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);

        copyObject(source::getClub, target::getClub, target::setClub, copyPolicy);
        copyObject(source::getClubActivity, target::getClubActivity, target::setClubActivity, copyPolicy);
        copyObject(source::getClubRole, target::getClubRole, target::setClubRole, copyPolicy);
        copyObject(source::getLiterature, target::getLiterature, target::setLiterature, copyPolicy);
        copyObject(source::getOther, target::getOther, target::setOther, copyPolicy);
        copyObject(source::getOtherInterest, target::getOtherInterest, target::setOtherInterest, copyPolicy);
        copyObject(source::getSport, target::getSport, target::setSport, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceReceiverClientInterest source, final ServiceReceiverClientInterestDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceReceiverClientInterestRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected ServiceReceiverClientInterest createFromInstance() {
        return new ServiceReceiverClientInterest();
    }

    @Override
    protected ServiceReceiverClientInterestDto createToInstance() {
        return new ServiceReceiverClientInterestDto();
    }
}

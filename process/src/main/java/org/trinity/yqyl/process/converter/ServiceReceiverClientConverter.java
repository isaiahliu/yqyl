package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientOtherDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.lookup.CredentialType;
import org.trinity.yqyl.common.message.lookup.FamilyRelationship;
import org.trinity.yqyl.common.message.lookup.Gender;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientHealthInformation;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientInterest;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientOther;
import org.trinity.yqyl.repository.business.entity.Yiquan;

@Component
public class ServiceReceiverClientConverter extends AbstractLookupSupportObjectConverter<ServiceReceiverClient, ServiceReceiverClientDto> {
    private static enum ServiceReceiverClientRelationship {
        YIQUAN,
        HEALTH_INFORMATION,
        INTEREST,
        OTHER,
        NA
    }

    @Autowired
    private IObjectConverter<ServiceReceiverClientHealthInformation, ServiceReceiverClientHealthInformationDto> serviceReceiverClientHealthInformationConverter;
    @Autowired
    private IObjectConverter<ServiceReceiverClientInterest, ServiceReceiverClientInterestDto> serviceReceiverClientInterestConverter;
    @Autowired
    private IObjectConverter<ServiceReceiverClientOther, ServiceReceiverClientOtherDto> serviceReceiverClientOtherConverter;

    @Autowired
    private IObjectConverter<Yiquan, YiquanDto> serviceReceiverClientYiquanConverter;

    @Autowired
    public ServiceReceiverClientConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceReceiverClientDto source, final ServiceReceiverClient target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, ServiceReceiverClientStatus.class, copyPolicy);
        copyLookup(source::getCredentialType, target::getCredentialType, target::setCredentialType, CredentialType.class, copyPolicy);
        copyLookup(source::getFamilyRelationship, target::getFamilyRelationship, target::setFamilyRelationship, FamilyRelationship.class,
                copyPolicy);
        copyLookup(source::getGender, target::getGender, target::setGender, Gender.class, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getCellphoneNo, target::getCellphoneNo, target::setCellphoneNo, copyPolicy);
        copyObject(source::getDob, target::getDob, target::setDob, copyPolicy);
        copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
        copyObject(source::getHomephoneNo, target::getHomephoneNo, target::setHomephoneNo, copyPolicy);
        copyObject(source::getIdentityCard, target::getIdentityCard, target::setIdentityCard, copyPolicy);
        copyObject(source::getIdentityCardCopy, target::getIdentityCardCopy, target::setIdentityCardCopy, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getNickname, target::getNickname, target::setNickname, copyPolicy);
        copyObject(source::getRegion, target::getRegion, target::setRegion, copyPolicy);
        copyObject(source::getEmergencyContact, target::getEmergencyContact, target::setEmergencyContact, copyPolicy);
        copyObject(source::getEmergencyContactNo, target::getEmergencyContactNo, target::setEmergencyContactNo, copyPolicy);
        copyObject(source::getRegistryDate, target::getRegistryDate, target::setRegistryDate, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceReceiverClient source, final ServiceReceiverClientDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyMessage(source::getGender, target::getGender, target::setGender, copyPolicy);
        copyMessage(source::getCredentialType, target::getCredentialType, target::setCredentialType, copyPolicy);
        copyMessage(source::getFamilyRelationship, target::getFamilyRelationship, target::setFamilyRelationship, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getCellphoneNo, target::getCellphoneNo, target::setCellphoneNo, copyPolicy);
        copyObject(source::getDob, target::getDob, target::setDob, copyPolicy);
        copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
        copyObject(source::getHomephoneNo, target::getHomephoneNo, target::setHomephoneNo, copyPolicy);
        copyObject(source::getIdentityCard, target::getIdentityCard, target::setIdentityCard, copyPolicy);
        copyObject(source::getIdentityCardCopy, target::getIdentityCardCopy, target::setIdentityCardCopy, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getNickname, target::getNickname, target::setNickname, copyPolicy);
        copyObject(source::getRegion, target::getRegion, target::setRegion, copyPolicy);
        copyObject(source::getEmergencyContact, target::getEmergencyContact, target::setEmergencyContact, copyPolicy);
        copyObject(source::getEmergencyContactNo, target::getEmergencyContactNo, target::setEmergencyContactNo, copyPolicy);
        copyObject(source::getRegistryDate, target::getRegistryDate, target::setRegistryDate, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceReceiverClient source, final ServiceReceiverClientDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceReceiverClientRelationship.class)) {
        case YIQUAN:
            copyRelationship(source::getYiquan, target::setYiquan, serviceReceiverClientYiquanConverter, relationshipExpression);
            break;
        case HEALTH_INFORMATION:
            copyRelationship(source::getHealthInformation, target::setHealthInformation, serviceReceiverClientHealthInformationConverter,
                    relationshipExpression);
            break;
        case INTEREST:
            copyRelationship(source::getInterest, target::setInterest, serviceReceiverClientInterestConverter, relationshipExpression);
            break;
        case OTHER:
            copyRelationship(source::getOther, target::setOther, serviceReceiverClientOtherConverter, relationshipExpression);
            break;
        case NA:
        default:
            break;
        }
    }

    @Override
    protected ServiceReceiverClient createFromInstance() {
        return new ServiceReceiverClient();
    }

    @Override
    protected ServiceReceiverClientDto createToInstance() {
        return new ServiceReceiverClientDto();
    }
}

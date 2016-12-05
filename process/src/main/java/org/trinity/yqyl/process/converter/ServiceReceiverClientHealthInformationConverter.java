package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientHealthInformation;

@Component
public class ServiceReceiverClientHealthInformationConverter
        extends AbstractLookupSupportObjectConverter<ServiceReceiverClientHealthInformation, ServiceReceiverClientHealthInformationDto> {
    private static enum ServiceReceiverClientHealthInformationRelationship {
        NA
    }

    @Autowired
    public ServiceReceiverClientHealthInformationConverter(
            final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceReceiverClientHealthInformationDto source,
            final ServiceReceiverClientHealthInformation target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getServiceReceiverClientId, target::setServiceReceiverClientId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);

        copyObject(source::getHealthStatus, target::getHealthStatus, target::setHealthStatus, copyPolicy);
        copyObject(source::getHearingStatus, target::getHearingStatus, target::setHearingStatus, copyPolicy);
        copyObject(source::getVisualAcuity, target::getVisualAcuity, target::setVisualAcuity, copyPolicy);
        copyObject(source::getSelfCareStatus, target::getSelfCareStatus, target::setSelfCareStatus, copyPolicy);
        copyObject(source::getIntellectualCondition, target::getIntellectualCondition, target::setIntellectualCondition, copyPolicy);
        copyObject(source::getActionCapacity, target::getActionCapacity, target::setActionCapacity, copyPolicy);
        copyObject(source::getTripMode, target::getTripMode, target::setTripMode, copyPolicy);
        copyObject(source::getMedicalHistory, target::getMedicalHistory, target::setMedicalHistory, copyPolicy);
        copyObject(source::getHeight, target::getHeight, target::setHeight, copyPolicy);
        copyObject(source::getWeight, target::getWeight, target::setWeight, copyPolicy);
        copyObject(source::getBloodType, target::getBloodType, target::setBloodType, copyPolicy);
        copyObject(source::getBloodPressure, target::getBloodPressure, target::setBloodPressure, copyPolicy);
        copyObject(source::getBloodSugar, target::getBloodSugar, target::setBloodSugar, copyPolicy);
        copyObject(source::getBloodFat, target::getBloodFat, target::setBloodFat, copyPolicy);
        copyObject(source::getHeartRate, target::getHeartRate, target::setHeartRate, copyPolicy);
        copyObject(source::getPulse, target::getPulse, target::setPulse, copyPolicy);
        copyObject(source::getRecentMedical, target::getRecentMedical, target::setRecentMedical, copyPolicy);
        copyObject(source::getAllergen, target::getAllergen, target::setAllergen, copyPolicy);
        copyObject(source::getOther, target::getOther, target::setOther, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceReceiverClientHealthInformation source,
            final ServiceReceiverClientHealthInformationDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getServiceReceiverClientId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);

        copyObject(source::getHealthStatus, target::getHealthStatus, target::setHealthStatus, copyPolicy);
        copyObject(source::getHearingStatus, target::getHearingStatus, target::setHearingStatus, copyPolicy);
        copyObject(source::getVisualAcuity, target::getVisualAcuity, target::setVisualAcuity, copyPolicy);
        copyObject(source::getSelfCareStatus, target::getSelfCareStatus, target::setSelfCareStatus, copyPolicy);
        copyObject(source::getIntellectualCondition, target::getIntellectualCondition, target::setIntellectualCondition, copyPolicy);
        copyObject(source::getActionCapacity, target::getActionCapacity, target::setActionCapacity, copyPolicy);
        copyObject(source::getTripMode, target::getTripMode, target::setTripMode, copyPolicy);
        copyObject(source::getMedicalHistory, target::getMedicalHistory, target::setMedicalHistory, copyPolicy);
        copyObject(source::getHeight, target::getHeight, target::setHeight, copyPolicy);
        copyObject(source::getWeight, target::getWeight, target::setWeight, copyPolicy);
        copyObject(source::getBloodType, target::getBloodType, target::setBloodType, copyPolicy);
        copyObject(source::getBloodPressure, target::getBloodPressure, target::setBloodPressure, copyPolicy);
        copyObject(source::getBloodSugar, target::getBloodSugar, target::setBloodSugar, copyPolicy);
        copyObject(source::getBloodFat, target::getBloodFat, target::setBloodFat, copyPolicy);
        copyObject(source::getHeartRate, target::getHeartRate, target::setHeartRate, copyPolicy);
        copyObject(source::getPulse, target::getPulse, target::setPulse, copyPolicy);
        copyObject(source::getRecentMedical, target::getRecentMedical, target::setRecentMedical, copyPolicy);
        copyObject(source::getAllergen, target::getAllergen, target::setAllergen, copyPolicy);
        copyObject(source::getOther, target::getOther, target::setOther, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceReceiverClientHealthInformation source,
            final ServiceReceiverClientHealthInformationDto target, final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceReceiverClientHealthInformationRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected ServiceReceiverClientHealthInformation createFromInstance() {
        return new ServiceReceiverClientHealthInformation();
    }

    @Override
    protected ServiceReceiverClientHealthInformationDto createToInstance() {
        return new ServiceReceiverClientHealthInformationDto();
    }
}

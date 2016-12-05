package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceReceiverClientHealthInformationDto extends AbstractBusinessDto {
    private String healthStatus;

    private String hearingStatus;

    private String visualAcuity;

    private String selfCareStatus;

    private String intellectualCondition;

    private String actionCapacity;

    private String tripMode;

    private String medicalHistory;

    private String height;

    private String weight;

    private String bloodFat;

    private String bloodSugar;

    private String bloodType;

    private String bloodPressure;

    private String heartRate;

    private String pulse;

    private String recentMedical;

    private String allergen;

    private String other;

    public String getActionCapacity() {
        return actionCapacity;
    }

    public String getAllergen() {
        return allergen;
    }

    public String getBloodFat() {
        return bloodFat;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getHearingStatus() {
        return hearingStatus;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getHeight() {
        return height;
    }

    public String getIntellectualCondition() {
        return intellectualCondition;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getOther() {
        return other;
    }

    public String getPulse() {
        return pulse;
    }

    public String getRecentMedical() {
        return recentMedical;
    }

    public String getSelfCareStatus() {
        return selfCareStatus;
    }

    public String getTripMode() {
        return tripMode;
    }

    public String getVisualAcuity() {
        return visualAcuity;
    }

    public String getWeight() {
        return weight;
    }

    public void setActionCapacity(final String actionCapacity) {
        this.actionCapacity = actionCapacity;
    }

    public void setAllergen(final String allergen) {
        this.allergen = allergen;
    }

    public void setBloodFat(final String bloodFat) {
        this.bloodFat = bloodFat;
    }

    public void setBloodPressure(final String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setBloodSugar(final String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public void setBloodType(final String bloodType) {
        this.bloodType = bloodType;
    }

    public void setHealthStatus(final String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setHearingStatus(final String hearingStatus) {
        this.hearingStatus = hearingStatus;
    }

    public void setHeartRate(final String heartRate) {
        this.heartRate = heartRate;
    }

    public void setHeight(final String height) {
        this.height = height;
    }

    public void setIntellectualCondition(final String intellectualCondition) {
        this.intellectualCondition = intellectualCondition;
    }

    public void setMedicalHistory(final String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setOther(final String other) {
        this.other = other;
    }

    public void setPulse(final String pulse) {
        this.pulse = pulse;
    }

    public void setRecentMedical(final String recentMedical) {
        this.recentMedical = recentMedical;
    }

    public void setSelfCareStatus(final String selfCareStatus) {
        this.selfCareStatus = selfCareStatus;
    }

    public void setTripMode(final String tripMode) {
        this.tripMode = tripMode;
    }

    public void setVisualAcuity(final String visualAcuity) {
        this.visualAcuity = visualAcuity;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }
}

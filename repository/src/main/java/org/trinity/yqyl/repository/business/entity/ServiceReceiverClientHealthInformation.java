//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_receiver_client_health_information database table.
 *
 */
@Entity
@Table(name = "service_receiver_client_health_information")
@NamedQuery(name = "ServiceReceiverClientHealthInformation.findAll", query = "SELECT s FROM ServiceReceiverClientHealthInformation s")
public class ServiceReceiverClientHealthInformation extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_receiver_client_id")
    private Long serviceReceiverClientId;

    @Column(name = "health_status")
    private String healthStatus;

    @Column(name = "hearing_status")
    private String hearingStatus;

    @Column(name = "visual_acuity")
    private String visualAcuity;

    @Column(name = "self_care_status")
    private String selfCareStatus;

    @Column(name = "intellectual_condition")
    private String intellectualCondition;

    @Column(name = "action_capacity")
    private String actionCapacity;

    @Column(name = "trip_mode")
    private String tripMode;

    @Column(name = "medical_history")
    private String medicalHistory;

    private String height;

    private String weight;

    @Column(name = "blood_fat")
    private String bloodFat;

    @Column(name = "blood_sugar")
    private String bloodSugar;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "blood_pressure")
    private String bloodPressure;

    @Column(name = "heart_rate")
    private String heartRate;

    private String pulse;

    @Column(name = "recent_medical")
    private String recentMedical;

    private String allergen;

    private String other;

    private RecordStatus status;

    // bi-directional one-to-one association to ServiceReceiverClient
    @OneToOne
    @JoinColumn(name = "service_receiver_client_id")
    private ServiceReceiverClient serviceReceiverClient;

    public ServiceReceiverClientHealthInformation() {
    }

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

    public ServiceReceiverClient getServiceReceiverClient() {
        return serviceReceiverClient;
    }

    public Long getServiceReceiverClientId() {
        return serviceReceiverClientId;
    }

    public RecordStatus getStatus() {
        return status;
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

    public void setServiceReceiverClient(final ServiceReceiverClient serviceReceiverClient) {
        this.serviceReceiverClient = serviceReceiverClient;
    }

    public void setServiceReceiverClientId(final Long serviceReceiverClientId) {
        this.serviceReceiverClientId = serviceReceiverClientId;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
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

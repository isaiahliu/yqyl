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
 * The persistent class for the service_receiver_client_other database table.
 *
 */
@Entity
@Table(name = "service_receiver_client_other")
@NamedQuery(name = "ServiceReceiverClientOther.findAll", query = "SELECT s FROM ServiceReceiverClientOther s")
public class ServiceReceiverClientOther extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_receiver_client_id")
    private Long serviceReceiverClientId;

    private String nationality;

    private String education;

    private String faith;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    private String other;

    private RecordStatus status;

    // bi-directional one-to-one association to ServiceReceiverClient
    @OneToOne
    @JoinColumn(name = "service_receiver_client_id")
    private ServiceReceiverClient serviceReceiverClient;

    public ServiceReceiverClientOther() {
    }

    public String getEducation() {
        return this.education;
    }

    public String getFaith() {
        return this.faith;
    }

    public String getNationality() {
        return this.nationality;
    }

    public String getOther() {
        return other;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public ServiceReceiverClient getServiceReceiverClient() {
        return this.serviceReceiverClient;
    }

    public Long getServiceReceiverClientId() {
        return this.serviceReceiverClientId;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public void setEducation(final String education) {
        this.education = education;
    }

    public void setFaith(final String faith) {
        this.faith = faith;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public void setOther(final String other) {
        this.other = other;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
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

}

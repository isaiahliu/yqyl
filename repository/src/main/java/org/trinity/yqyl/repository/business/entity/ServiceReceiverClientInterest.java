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
 * The persistent class for the service_receiver_client_interest database table.
 *
 */
@Entity
@Table(name = "service_receiver_client_interest")
@NamedQuery(name = "ServiceReceiverClientInterest.findAll", query = "SELECT s FROM ServiceReceiverClientInterest s")
public class ServiceReceiverClientInterest extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_receiver_client_id")
    private Long serviceReceiverClientId;

    private String literature;

    private String sport;

    @Column(name = "other_interest")
    private String otherInterest;

    private String club;

    @Column(name = "club_activity")
    private String clubActivity;

    @Column(name = "club_role")
    private String clubRole;

    private String other;

    private RecordStatus status;

    // bi-directional one-to-one association to ServiceReceiverClient
    @OneToOne
    @JoinColumn(name = "service_receiver_client_id")
    private ServiceReceiverClient serviceReceiverClient;

    public ServiceReceiverClientInterest() {
    }

    public String getClub() {
        return club;
    }

    public String getClubActivity() {
        return clubActivity;
    }

    public String getClubRole() {
        return clubRole;
    }

    public String getLiterature() {
        return literature;
    }

    public String getOther() {
        return other;
    }

    public String getOtherInterest() {
        return otherInterest;
    }

    public ServiceReceiverClient getServiceReceiverClient() {
        return serviceReceiverClient;
    }

    public Long getServiceReceiverClientId() {
        return serviceReceiverClientId;
    }

    public String getSport() {
        return sport;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setClub(final String club) {
        this.club = club;
    }

    public void setClubActivity(final String clubActivity) {
        this.clubActivity = clubActivity;
    }

    public void setClubRole(final String clubRole) {
        this.clubRole = clubRole;
    }

    public void setLiterature(final String literature) {
        this.literature = literature;
    }

    public void setOther(final String other) {
        this.other = other;
    }

    public void setOtherInterest(final String otherInterest) {
        this.otherInterest = otherInterest;
    }

    public void setServiceReceiverClient(final ServiceReceiverClient serviceReceiverClient) {
        this.serviceReceiverClient = serviceReceiverClient;
    }

    public void setServiceReceiverClientId(final Long serviceReceiverClientId) {
        this.serviceReceiverClientId = serviceReceiverClientId;
    }

    public void setSport(final String sport) {
        this.sport = sport;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }
}

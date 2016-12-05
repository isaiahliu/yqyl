//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the yiquan database table.
 *
 */
@Entity
@NamedQuery(name = "Yiquan.findAll", query = "SELECT y FROM Yiquan y")
public class Yiquan extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private RecordStatus status;

    // bi-directional many-to-one association to User
    @OneToMany(mappedBy = "yiquan")
    private List<ServiceReceiverClient> serviceReceiverClients;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Yiquan() {
    }

    public Account getAccount() {
        return this.account;
    }

    public String getCode() {
        return this.code;
    }

    public Long getId() {
        return id;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public List<ServiceReceiverClient> getServiceReceiverClients() {
        return serviceReceiverClients;
    }

    public void setServiceReceiverClients(List<ServiceReceiverClient> serviceReceiverClients) {
        this.serviceReceiverClients = serviceReceiverClients;
    }
}

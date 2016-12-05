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
import org.trinity.yqyl.common.message.lookup.AccountType;
import org.trinity.yqyl.common.message.lookup.Bank;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_supplier_client_account database table.
 *
 */
@Entity
@Table(name = "service_supplier_client_account")
@NamedQuery(name = "ServiceSupplierClientAccount.findAll", query = "SELECT s FROM ServiceSupplierClientAccount s")
public class ServiceSupplierClientAccount extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_supplier_client_id")
    private Long serviceSupplierClientId;

    private String account;

    private Bank bank;

    @Column(name = "client_name")
    private String clientName;

    private RecordStatus status;

    private AccountType type;

    // bi-directional one-to-one association to ServiceSupplierClient
    @OneToOne
    @JoinColumn(name = "service_supplier_client_id")
    private ServiceSupplierClient serviceSupplierClient;

    public ServiceSupplierClientAccount() {
    }

    public String getAccount() {
        return this.account;
    }

    public Bank getBank() {
        return this.bank;
    }

    public String getClientName() {
        return this.clientName;
    }

    public ServiceSupplierClient getServiceSupplierClient() {
        return this.serviceSupplierClient;
    }

    public Long getServiceSupplierClientId() {
        return this.serviceSupplierClientId;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public AccountType getType() {
        return this.type;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public void setBank(final Bank bank) {
        this.bank = bank;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
        this.serviceSupplierClient = serviceSupplierClient;
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public void setType(final AccountType type) {
        this.type = type;
    }

}

//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.CompanyType;
import org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus;

/**
 * The persistent class for the service_supplier_client database table.
 *
 */
@Entity
@Table(name = "service_supplier_client")
@NamedQuery(name = "ServiceSupplierClient.findAll", query = "SELECT s FROM ServiceSupplierClient s")
public class ServiceSupplierClient extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String address;

    // bi-directional many-to-one association to ServiceSupplierStaff
    @OneToMany(mappedBy = "serviceSupplierClient")
    private List<ServiceSupplierStaff> serviceSupplierStaffs;

    // bi-directional one-to-one association to User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // bi-directional many-to-one association to ServiceInfo
    @OneToMany(mappedBy = "serviceSupplierClient")
    private List<ServiceInfo> serviceInfos;

    private String description;

    private String email;

    private String logo;

    private String name;

    private String contact;

    @Column(name = "contact_phone")
    private String contactPhone;

    private String region;

    @Column(name = "service_categories")
    private String serviceCategories;

    @Column(name = "service_phone")
    private String servicePhone;
    private CompanyType type;

    private ServiceSupplierClientStatus status;

    // bi-directional one-to-one association to ServiceSupplierClientAccount
    @OneToOne(mappedBy = "serviceSupplierClient")
    private ServiceSupplierClientAccount bankAccount;

    // bi-directional one-to-one association to ServiceSupplierClientMaterial
    @OneToOne(mappedBy = "serviceSupplierClient")
    private ServiceSupplierClientMaterial material;

    @OneToMany(mappedBy = "serviceSupplierClient")
    private List<ServiceSupplierClientAuditing> auditings;

    // bi-directional many-to-one association to Account
    @ManyToOne
    private Account account;

    // bi-directional many-to-many association to UserGroup
    @ElementCollection
    @Temporal(TemporalType.TIMESTAMP)
    @CollectionTable(name = "service_supplier_client_requirement", joinColumns = @JoinColumn(name = "service_supplier_client_id"))
    @Column(name = "last_read_timestamp")
    private List<Date> lastReadTimestamps;

    public ServiceSupplierClient() {
    }

    public ServiceSupplierClientAuditing addAuditing(final ServiceSupplierClientAuditing auditing) {
        getAuditings().add(auditing);
        auditing.setServiceSupplierClient(this);

        return auditing;
    }

    public ServiceInfo addServiceInfo(final ServiceInfo serviceInfo) {
        getServiceInfos().add(serviceInfo);
        serviceInfo.setServiceSupplierClient(this);

        return serviceInfo;
    }

    public ServiceSupplierStaff addServiceSupplierStaff(final ServiceSupplierStaff serviceSupplierStaff) {
        getServiceSupplierStaffs().add(serviceSupplierStaff);
        serviceSupplierStaff.setServiceSupplierClient(this);

        return serviceSupplierStaff;
    }

    public Account getAccount() {
        return this.account;
    }

    public String getAddress() {
        return this.address;
    }

    public List<ServiceSupplierClientAuditing> getAuditings() {
        return this.auditings;
    }

    public ServiceSupplierClientAccount getBankAccount() {
        return bankAccount;
    }

    public String getContact() {
        return this.contact;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Date> getLastReadTimestamps() {
        return lastReadTimestamps;
    }

    public String getLogo() {
        return this.logo;
    }

    public ServiceSupplierClientMaterial getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }

    public String getRegion() {
        return this.region;
    }

    public String getServiceCategories() {
        return this.serviceCategories;
    }

    public List<ServiceInfo> getServiceInfos() {
        return this.serviceInfos;
    }

    public String getServicePhone() {
        return this.servicePhone;
    }

    public List<ServiceSupplierStaff> getServiceSupplierStaffs() {
        return this.serviceSupplierStaffs;
    }

    public ServiceSupplierClientStatus getStatus() {
        return this.status;
    }

    public CompanyType getType() {
        return this.type;
    }

    public User getUser() {
        return this.user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public ServiceSupplierClientAuditing removeAuditing(final ServiceSupplierClientAuditing auditing) {
        getAuditings().remove(auditing);
        auditing.setServiceSupplierClient(null);

        return auditing;
    }

    public ServiceInfo removeServiceInfo(final ServiceInfo serviceInfo) {
        getServiceInfos().remove(serviceInfo);
        serviceInfo.setServiceSupplierClient(null);

        return serviceInfo;
    }

    public ServiceSupplierStaff removeServiceSupplierStaff(final ServiceSupplierStaff serviceSupplierStaff) {
        getServiceSupplierStaffs().remove(serviceSupplierStaff);
        serviceSupplierStaff.setServiceSupplierClient(null);

        return serviceSupplierStaff;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAuditings(final List<ServiceSupplierClientAuditing> auditings) {
        this.auditings = auditings;
    }

    public void setBankAccount(final ServiceSupplierClientAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setLastReadTimestamps(final List<Date> lastReadTimestamps) {
        this.lastReadTimestamps = lastReadTimestamps;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public void setMaterial(final ServiceSupplierClientMaterial material) {
        this.material = material;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public void setServiceCategories(final String serviceCategories) {
        this.serviceCategories = serviceCategories;
    }

    public void setServiceInfos(final List<ServiceInfo> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }

    public void setServicePhone(final String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public void setServiceSupplierStaffs(final List<ServiceSupplierStaff> serviceSupplierStaffs) {
        this.serviceSupplierStaffs = serviceSupplierStaffs;
    }

    public void setStatus(final ServiceSupplierClientStatus status) {
        this.status = status;
    }

    public void setType(final CompanyType type) {
        this.type = type;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    } // bi-directional many-to-one association to ServiceSupplierClientAuditing
}

//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.StaffStatus;

/**
 * The persistent class for the service_supplier_staff database table.
 *
 */
@Entity
@Table(name = "service_supplier_staff")
@NamedQuery(name = "ServiceSupplierStaff.findAll", query = "SELECT s FROM ServiceSupplierStaff s")
public class ServiceSupplierStaff extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceSupplierStaff_PK_IdGenerator")
    @TableGenerator(name = "ServiceSupplierStaff_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceSupplierStaff_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
    private Long id;

    private String code;

    private String comment;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "identity_card")
    private String identityCard;

    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    private String photo;

    private StaffStatus status;

    // bi-directional many-to-one association to ServiceOrder
    @OneToMany(mappedBy = "serviceSupplierStaff")
    private List<ServiceOrder> serviceOrders;

    // bi-directional many-to-one association to ServiceSupplierClient
    @ManyToOne
    @JoinColumn(name = "service_supplier_client_id")
    private ServiceSupplierClient serviceSupplierClient;

    // bi-directional many-to-many association to ServiceCategory
    @ManyToMany
    @JoinTable(name = "service_supplier_staff_service_category", joinColumns = {
            @JoinColumn(name = "service_supplier_staff_id") }, inverseJoinColumns = { @JoinColumn(name = "service_category_id") })
    private List<ServiceCategory> serviceCategories;

    public ServiceSupplierStaff() {
    }

    public ServiceOrder addServiceOrder(final ServiceOrder serviceOrder) {
        getServiceOrders().add(serviceOrder);
        serviceOrder.setServiceSupplierStaff(this);

        return serviceOrder;
    }

    public String getCode() {
        return code;
    }

    public String getComment() {
        return this.comment;
    }

    public Date getDob() {
        return this.dob;
    }

    public Long getId() {
        return this.id;
    }

    public String getIdentityCard() {
        return this.identityCard;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public String getPhoto() {
        return this.photo;
    }

    public List<ServiceCategory> getServiceCategories() {
        return this.serviceCategories;
    }

    public List<ServiceOrder> getServiceOrders() {
        return this.serviceOrders;
    }

    public ServiceSupplierClient getServiceSupplierClient() {
        return this.serviceSupplierClient;
    }

    public StaffStatus getStatus() {
        return this.status;
    }

    public ServiceOrder removeServiceOrder(final ServiceOrder serviceOrder) {
        getServiceOrders().remove(serviceOrder);
        serviceOrder.setServiceSupplierStaff(null);

        return serviceOrder;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setIdentityCard(final String identityCard) {
        this.identityCard = identityCard;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public void setServiceCategories(final List<ServiceCategory> serviceCategories) {
        this.serviceCategories = serviceCategories;
    }

    public void setServiceOrders(final List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
        this.serviceSupplierClient = serviceSupplierClient;
    }

    public void setStatus(final StaffStatus status) {
        this.status = status;
    }

}

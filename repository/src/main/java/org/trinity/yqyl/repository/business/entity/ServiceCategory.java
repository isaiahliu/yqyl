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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_category database table.
 *
 */
@Entity
@Table(name = "service_category")
@NamedQuery(name = "ServiceCategory.findAll", query = "SELECT s FROM ServiceCategory s")
public class ServiceCategory extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", insertable = true, updatable = true)
    private String image;

    private String description;

    private String name;

    private RecordStatus status;

    // bi-directional many-to-one association to ServiceCategory
    @ManyToOne
    @JoinColumn(name = "parent_service_category_id")
    private ServiceCategory parent;

    // bi-directional many-to-one association to ServiceCategory
    @OneToMany(mappedBy = "parent")
    private List<ServiceCategory> children;
    // bi-directional many-to-one association to ServiceInfo
    @OneToMany(mappedBy = "serviceCategory")
    private List<ServiceInfo> serviceInfos;
    // bi-directional many-to-many association to ServiceSupplierStaff
    @ManyToMany(mappedBy = "serviceCategories")
    private List<ServiceSupplierStaff> serviceSupplierStaffs;

    public ServiceCategory() {
    }

    public ServiceCategory addChildren(final ServiceCategory children) {
        getChildren().add(children);
        children.setParent(this);

        return children;
    }

    public ServiceInfo addServiceInfo(final ServiceInfo serviceInfo) {
        getServiceInfos().add(serviceInfo);
        serviceInfo.setServiceCategory(this);

        return serviceInfo;
    }

    public List<ServiceCategory> getChildren() {
        return this.children;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getId() {
        return this.id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return this.name;
    }

    public ServiceCategory getParent() {
        return this.parent;
    }

    public List<ServiceInfo> getServiceInfos() {
        return this.serviceInfos;
    }

    public List<ServiceSupplierStaff> getServiceSupplierStaffs() {
        return this.serviceSupplierStaffs;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public ServiceCategory removeChildren(final ServiceCategory children) {
        getChildren().remove(children);
        children.setParent(null);

        return children;
    }

    public ServiceInfo removeServiceInfo(final ServiceInfo serviceInfo) {
        getServiceInfos().remove(serviceInfo);
        serviceInfo.setServiceCategory(null);

        return serviceInfo;
    }

    public void setChildren(final List<ServiceCategory> children) {
        this.children = children;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParent(final ServiceCategory parent) {
        this.parent = parent;
    }

    public void setServiceInfos(final List<ServiceInfo> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }

    public void setServiceSupplierStaffs(final List<ServiceSupplierStaff> serviceSupplierStaffs) {
        this.serviceSupplierStaffs = serviceSupplierStaffs;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

}

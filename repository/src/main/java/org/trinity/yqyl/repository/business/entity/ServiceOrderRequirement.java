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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;

/**
 * The persistent class for the service_order_requirement database table.
 *
 */
@Entity
@Table(name = "service_order_requirement")
@NamedQuery(name = "ServiceOrderRequirement.findAll", query = "SELECT s FROM ServiceOrderRequirement s")
public class ServiceOrderRequirement extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceOrderRequirement_PK_IdGenerator")
	@TableGenerator(name = "ServiceOrderRequirement_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceOrderRequirement_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	private String address;

	@Column(name = "announce_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date announceTime;

	private String comment;

	private String phone;

	private Double price;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "service_time")
	private Date serviceTime;

	private ServiceOrderRequirementStatus status;

	// bi-directional many-to-one association to ServiceOrder
	@OneToMany(mappedBy = "serviceOrderRequirement")
	private List<ServiceOrder> serviceOrders;

	// bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public ServiceOrderRequirement() {
	}

	public ServiceOrder addServiceOrder(final ServiceOrder serviceOrder) {
		getServiceOrders().add(serviceOrder);
		serviceOrder.setServiceOrderRequirement(this);

		return serviceOrder;
	}

	public String getAddress() {
		return this.address;
	}

	public Date getAnnounceTime() {
		return announceTime;
	}

	public String getComment() {
		return this.comment;
	}

	public Long getId() {
		return this.id;
	}

	public String getPhone() {
		return this.phone;
	}

	public Double getPrice() {
		return this.price;
	}

	public List<ServiceOrder> getServiceOrders() {
		return this.serviceOrders;
	}

	public Date getServiceTime() {
		return this.serviceTime;
	}

	public ServiceOrderRequirementStatus getStatus() {
		return this.status;
	}

	public User getUser() {
		return user;
	}

	public ServiceOrder removeServiceOrder(final ServiceOrder serviceOrder) {
		getServiceOrders().remove(serviceOrder);
		serviceOrder.setServiceOrderRequirement(null);

		return serviceOrder;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setAnnounceTime(final Date announceTime) {
		this.announceTime = announceTime;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public void setServiceOrders(final List<ServiceOrder> serviceOrders) {
		this.serviceOrders = serviceOrders;
	}

	public void setServiceTime(final Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setStatus(final ServiceOrderRequirementStatus status) {
		this.status = status;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}

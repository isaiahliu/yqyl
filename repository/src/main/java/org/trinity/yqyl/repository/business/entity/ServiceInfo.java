//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.City;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;
import org.trinity.yqyl.common.message.lookup.Province;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;

/**
 * The persistent class for the service database table.
 *
 */
@Entity
@Table(name = "service_info")
@NamedQuery(name = "ServiceInfo.findAll", query = "SELECT s FROM ServiceInfo s")
public class ServiceInfo extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Service_PK_IdGenerator")
	@TableGenerator(name = "Service_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "Service_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	// bi-directional many-to-many association to UserGroup
	@ElementCollection
	@CollectionTable(name = "service_info_image", joinColumns = @JoinColumn(name = "service_info_id"))
	@Column(name = "uuid")
	private List<String> images;

	private String description;

	private String name;

	private Province province;

	private City city;

	private Double price;

	@Column(name = "image", insertable = true, updatable = true)
	private String image;

	private ServiceStatus status;

	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "payment_type")
	private PaymentType paymentType;

	// bi-directional many-to-one association to Favorite
	@OneToMany(mappedBy = "service")
	private List<Favorite> favorites;

	// bi-directional many-to-one association to ServiceCategory
	@ManyToOne
	@JoinColumn(name = "service_category_id")
	private ServiceCategory serviceCategory;

	// bi-directional many-to-one association to ServiceSupplierClient
	@ManyToOne
	@JoinColumn(name = "service_supplier_client_id")
	private ServiceSupplierClient serviceSupplierClient;

	// bi-directional many-to-one association to ServiceOrder
	@OneToMany(mappedBy = "serviceInfo")
	private List<ServiceOrder> serviceOrders;

	// bi-directional one-to-one association to ServiceInfoStastic
	@OneToOne(mappedBy = "serviceInfo")
	private ServiceInfoStastic serviceInfoStastic;

	public ServiceInfo() {
	}

	public Favorite addFavorite(final Favorite favorite) {
		getFavorites().add(favorite);
		favorite.setService(this);

		return favorite;
	}

	public ServiceOrder addServiceOrder(final ServiceOrder serviceOrder) {
		getServiceOrders().add(serviceOrder);
		serviceOrder.setServiceInfo(this);

		return serviceOrder;
	}

	public City getCity() {
		return city;
	}

	public String getDescription() {
		return this.description;
	}

	public List<Favorite> getFavorites() {
		return this.favorites;
	}

	public Long getId() {
		return this.id;
	}

	public String getImage() {
		return image;
	}

	public List<String> getImages() {
		return images;
	}

	public String getName() {
		return this.name;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public Double getPrice() {
		return this.price;
	}

	public Province getProvince() {
		return province;
	}

	public ServiceCategory getServiceCategory() {
		return this.serviceCategory;
	}

	public ServiceInfoStastic getServiceInfoStastic() {
		return serviceInfoStastic;
	}

	public List<ServiceOrder> getServiceOrders() {
		return this.serviceOrders;
	}

	public ServiceSupplierClient getServiceSupplierClient() {
		return this.serviceSupplierClient;
	}

	public ServiceStatus getStatus() {
		return this.status;
	}

	public Favorite removeFavorite(final Favorite favorite) {
		getFavorites().remove(favorite);
		favorite.setService(null);

		return favorite;
	}

	public ServiceOrder removeServiceOrder(final ServiceOrder serviceOrder) {
		getServiceOrders().remove(serviceOrder);
		serviceOrder.setServiceInfo(null);

		return serviceOrder;
	}

	public void setCity(final City city) {
		this.city = city;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setFavorites(final List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public void setImages(final List<String> images) {
		this.images = images;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPaymentMethod(final PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setPaymentType(final PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public void setProvince(final Province province) {
		this.province = province;
	}

	public void setServiceCategory(final ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public void setServiceInfoStastic(final ServiceInfoStastic serviceInfoStastic) {
		this.serviceInfoStastic = serviceInfoStastic;
	}

	public void setServiceOrders(final List<ServiceOrder> serviceOrders) {
		this.serviceOrders = serviceOrders;
	}

	public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
		this.serviceSupplierClient = serviceSupplierClient;
	}

	public void setStatus(final ServiceStatus status) {
		this.status = status;
	}

}

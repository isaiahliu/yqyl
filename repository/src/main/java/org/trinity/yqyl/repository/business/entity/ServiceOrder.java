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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;

/**
 * The persistent class for the order database table.
 *
 */
@Entity
@Table(name = "service_order")
@NamedQuery(name = "ServiceOrder.findAll", query = "SELECT o FROM ServiceOrder o")
public class ServiceOrder extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phone;

	private String address;

	// bi-directional many-to-one association to ServiceOrderRequirement
	@ManyToOne
	@JoinColumn(name = "service_order_requirement_id")
	private ServiceOrderRequirement serviceOrderRequirement;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "service_time")
	private Date serviceTime;

	// bi-directional many-to-one association to ServiceOrderOperation
	@OneToMany(mappedBy = "serviceOrder")
	private List<ServiceOrderOperation> operations;

	// bi-directional many-to-one association to ServiceInfo
	@ManyToOne
	@JoinColumn(name = "service_info_id")
	private ServiceInfo serviceInfo;

	// bi-directional many-to-one association to ServiceSupplierStaff
	@ManyToOne
	@JoinColumn(name = "service_supplier_staff_id")
	private ServiceSupplierStaff serviceSupplierStaff;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceOrder_PK_IdGenerator")
	@TableGenerator(name = "ServiceOrder_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceOrder_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	private Double price;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "proposal_time")
	private Date proposalTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_time")
	private Date approvalTime;

	@Column(name = "expected_payment_amount")
	private Double expectedPaymentAmount;

	@Column(name = "actual_payment_amount")
	private Double actualPaymentAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "settled_time")
	private Date settledTime;

	@Column(name = "receipt", insertable = true, updatable = true)
	private String receipt;

	private OrderStatus status;

	// bi-directional one-to-one association to ServiceOrderAppraise
	@OneToOne(mappedBy = "serviceOrder")
	private ServiceOrderAppraise appraise;

	// bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;

	@Column(name = "payment_type")
	private PaymentType paymentType;

	@OneToOne
	@JoinColumn(name = "account_transaction_id")
	private AccountTransaction accountTransaction;

	public ServiceOrder() {
	}

	public ServiceOrderOperation addOperation(final ServiceOrderOperation operation) {
		getOperations().add(operation);
		operation.setServiceOrder(this);

		return operation;
	}

	public AccountTransaction getAccountTransaction() {
		return accountTransaction;
	}

	public Double getActualPaymentAmount() {
		return actualPaymentAmount;
	}

	public String getAddress() {
		return address;
	}

	public ServiceOrderAppraise getAppraise() {
		return this.appraise;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public Double getExpectedPaymentAmount() {
		return expectedPaymentAmount;
	}

	public Long getId() {
		return this.id;
	}

	public List<ServiceOrderOperation> getOperations() {
		return this.operations;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public String getPhone() {
		return phone;
	}

	public Double getPrice() {
		return this.price;
	}

	public Date getProposalTime() {
		return proposalTime;
	}

	public String getReceipt() {
		return receipt;
	}

	public ServiceInfo getServiceInfo() {
		return this.serviceInfo;
	}

	public ServiceOrderRequirement getServiceOrderRequirement() {
		return this.serviceOrderRequirement;
	}

	public ServiceSupplierStaff getServiceSupplierStaff() {
		return this.serviceSupplierStaff;
	}

	public Date getServiceTime() {
		return serviceTime;
	}

	public Date getSettledTime() {
		return settledTime;
	}

	public OrderStatus getStatus() {
		return this.status;
	}

	public User getUser() {
		return user;
	}

	public ServiceOrderOperation removeOperation(final ServiceOrderOperation operation) {
		getOperations().remove(operation);
		operation.setServiceOrder(null);

		return operation;
	}

	public void setAccountTransaction(final AccountTransaction accountTransaction) {
		this.accountTransaction = accountTransaction;
	}

	public void setActualPaymentAmount(final Double actualPaymentAmount) {
		this.actualPaymentAmount = actualPaymentAmount;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setAppraise(final ServiceOrderAppraise appraise) {
		this.appraise = appraise;
	}

	public void setApprovalTime(final Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public void setExpectedPaymentAmount(final Double expectedPaymentAmount) {
		this.expectedPaymentAmount = expectedPaymentAmount;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setOperations(final List<ServiceOrderOperation> operations) {
		this.operations = operations;
	}

	public void setPaymentMethod(final PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setPaymentType(final PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public void setProposalTime(final Date proposalTime) {
		this.proposalTime = proposalTime;
	}

	public void setReceipt(final String receipt) {
		this.receipt = receipt;
	}

	public void setServiceInfo(final ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public void setServiceOrderRequirement(final ServiceOrderRequirement serviceOrderRequirement) {
		this.serviceOrderRequirement = serviceOrderRequirement;
	}

	public void setServiceSupplierStaff(final ServiceSupplierStaff serviceSupplierStaff) {
		this.serviceSupplierStaff = serviceSupplierStaff;
	}

	public void setServiceTime(final Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public void setSettledTime(final Date settledTime) {
		this.settledTime = settledTime;
	}

	public void setStatus(final OrderStatus status) {
		this.status = status;
	}

	public void setUser(final User user) {
		this.user = user;
	}
}

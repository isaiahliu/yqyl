//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.AccountStatus;

/**
 * The persistent class for the account database table.
 *
 */
@Entity
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Account_PK_IdGenerator")
	@TableGenerator(name = "Account_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "Account_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	private AccountStatus status;

	// bi-directional many-to-one association to Yiquan
	@OneToMany(mappedBy = "account")
	private List<Yiquan> yiquans;

	// bi-directional many-to-one association to Yiquan
	@OneToMany(mappedBy = "account")
	private List<User> users;

	// bi-directional many-to-one association to ServiceSupplierClient
	@OneToMany(mappedBy = "account")
	private List<ServiceSupplierClient> serviceSupplierClients;

	// bi-directional many-to-one association to AccountBalance
	@OneToMany(mappedBy = "account")
	private List<AccountBalance> balances;

	public Account() {
	}

	public AccountBalance addBalance(final AccountBalance balance) {
		getBalances().add(balance);
		balance.setAccount(this);

		return balance;
	}

	public ServiceSupplierClient addServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
		getServiceSupplierClients().add(serviceSupplierClient);
		serviceSupplierClient.setAccount(this);

		return serviceSupplierClient;
	}

	public Yiquan addYiquan(final Yiquan yiquan) {
		getYiquans().add(yiquan);
		yiquan.setAccount(this);

		return yiquan;
	}

	public List<AccountBalance> getBalances() {
		return this.balances;
	}

	public Long getId() {
		return id;
	}

	public List<ServiceSupplierClient> getServiceSupplierClients() {
		return this.serviceSupplierClients;
	}

	public AccountStatus getStatus() {
		return this.status;
	}

	public List<Yiquan> getYiquans() {
		return this.yiquans;
	}

	public AccountBalance removeBalance(final AccountBalance balance) {
		getBalances().remove(balance);
		balance.setAccount(null);

		return balance;
	}

	public ServiceSupplierClient removeServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
		getServiceSupplierClients().remove(serviceSupplierClient);
		serviceSupplierClient.setAccount(null);

		return serviceSupplierClient;
	}

	public Yiquan removeYiquan(final Yiquan Yiquan) {
		getYiquans().remove(Yiquan);
		Yiquan.setAccount(null);

		return Yiquan;
	}

	public void setBalances(final List<AccountBalance> balances) {
		this.balances = balances;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setServiceSupplierClients(final List<ServiceSupplierClient> serviceSupplierClients) {
		this.serviceSupplierClients = serviceSupplierClients;
	}

	public void setStatus(final AccountStatus status) {
		this.status = status;
	}

	public void setYiquans(final List<Yiquan> yiquans) {
		this.yiquans = yiquans;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

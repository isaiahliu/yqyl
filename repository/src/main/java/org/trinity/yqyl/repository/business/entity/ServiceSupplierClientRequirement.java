//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractEntity;

/**
 * The persistent class for the service_supplier_client_requirement database
 * table.
 * 
 */
@Entity
@Table(name = "service_supplier_client_requirement")
@NamedQuery(name = "ServiceSupplierClientRequirement.findAll", query = "SELECT s FROM ServiceSupplierClientRequirement s")
public class ServiceSupplierClientRequirement extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "service_supplier_client_id")
	private Long serviceSupplierClientId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_read_timestamp")
	private Date lastReadTimestamp;

	// bi-directional one-to-one association to ServiceSupplierClient
	@OneToOne
	@JoinColumn(name = "service_supplier_client_id")
	private ServiceSupplierClient serviceSupplierClient;

	public ServiceSupplierClientRequirement() {
	}

	public Date getLastReadTimestamp() {
		return this.lastReadTimestamp;
	}

	public ServiceSupplierClient getServiceSupplierClient() {
		return this.serviceSupplierClient;
	}

	public Long getServiceSupplierClientId() {
		return this.serviceSupplierClientId;
	}

	public void setLastReadTimestamp(final Date lastReadTimestamp) {
		this.lastReadTimestamp = lastReadTimestamp;
	}

	public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
		this.serviceSupplierClient = serviceSupplierClient;
	}

	public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
		this.serviceSupplierClientId = serviceSupplierClientId;
	}

}

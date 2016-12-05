//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.FavoriteCategory;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the favorite database table.
 *
 */
@Entity
@NamedQuery(name = "Favorite.findAll", query = "SELECT f FROM Favorite f")
public class Favorite extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private FavoriteCategory category;

	private RecordStatus status;

	// bi-directional many-to-one association to Service
	@ManyToOne
	private ServiceInfo service;

	// bi-directional many-to-one association to ServiceReceiverClient
	@ManyToOne
	@JoinColumn(name = "service_receiver_client_id")
	private ServiceReceiverClient serviceReceiverClient;

	public Favorite() {
	}

	public FavoriteCategory getCategory() {
		return this.category;
	}

	public Long getId() {
		return this.id;
	}

	public ServiceInfo getService() {
		return this.service;
	}

	public ServiceReceiverClient getServiceReceiverClient() {
		return this.serviceReceiverClient;
	}

	public RecordStatus getStatus() {
		return this.status;
	}

	public void setCategory(final FavoriteCategory category) {
		this.category = category;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setService(final ServiceInfo service) {
		this.service = service;
	}

	public void setServiceReceiverClient(final ServiceReceiverClient serviceReceiverClient) {
		this.serviceReceiverClient = serviceReceiverClient;
	}

	public void setStatus(final RecordStatus status) {
		this.status = status;
	}

}

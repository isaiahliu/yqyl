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
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_order_appraise database table.
 *
 */
@Entity
@Table(name = "service_order_appraise")
@NamedQuery(name = "ServiceOrderAppraise.findAll", query = "SELECT s FROM ServiceOrderAppraise s")
public class ServiceOrderAppraise extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "service_order_id")
	private Long serviceOrderId;

	@Column(name = "attitude_rate")
	private Integer attitudeRate;

	private String comment;

	private String reply;
	@Column(name = "on_time_rate")
	private Integer onTimeRate;

	@Column(name = "quality_rate")
	private Integer qualityRate;

	@Column(name = "staff_rate")
	private Integer staffRate;

	private RecordStatus status;

	// bi-directional one-to-one association to ServiceOrder
	@OneToOne
	@JoinColumn(name = "service_order_id")
	private ServiceOrder serviceOrder;

	public ServiceOrderAppraise() {
	}

	public Integer getAttitudeRate() {
		return this.attitudeRate;
	}

	public String getComment() {
		return this.comment;
	}

	public Integer getOnTimeRate() {
		return this.onTimeRate;
	}

	public Integer getQualityRate() {
		return this.qualityRate;
	}

	public String getReply() {
		return reply;
	}

	public ServiceOrder getServiceOrder() {
		return this.serviceOrder;
	}

	public Long getServiceOrderId() {
		return this.serviceOrderId;
	}

	public Integer getStaffRate() {
		return this.staffRate;
	}

	public RecordStatus getStatus() {
		return this.status;
	}

	public void setAttitudeRate(final Integer attitudeRate) {
		this.attitudeRate = attitudeRate;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setOnTimeRate(final Integer onTimeRate) {
		this.onTimeRate = onTimeRate;
	}

	public void setQualityRate(final Integer qualityRate) {
		this.qualityRate = qualityRate;
	}

	public void setReply(final String reply) {
		this.reply = reply;
	}

	public void setServiceOrder(final ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public void setServiceOrderId(final Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public void setStaffRate(final Integer staffRate) {
		this.staffRate = staffRate;
	}

	public void setStatus(final RecordStatus status) {
		this.status = status;
	}

}

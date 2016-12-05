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
//Cleaned

import org.trinity.repository.entity.AbstractEntity;

/**
 * The persistent class for the service_info_stastic database table.
 *
 */
@Entity
@Table(name = "service_info_stastic")
@NamedQuery(name = "ServiceInfoStastic.findAll", query = "SELECT s FROM ServiceInfoStastic s")
public class ServiceInfoStastic extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "service_info_id")
    private Long serviceInfoId;

    @Column(name = "appraise_avg")
    private Double appraiseAvg;

    @Column(name = "appraise_count")
    private Long appraiseCount;

    @Column(name = "order_count")
    private Long orderCount;

    // bi-directional one-to-one association to ServiceInfo
    @OneToOne
    @JoinColumn(name = "service_info_id")
    private ServiceInfo serviceInfo;

    public ServiceInfoStastic() {
    }

    public Double getAppraiseAvg() {
        return this.appraiseAvg;
    }

    public Long getAppraiseCount() {
        return this.appraiseCount;
    }

    public Long getOrderCount() {
        return this.orderCount;
    }

    public ServiceInfo getServiceInfo() {
        return this.serviceInfo;
    }

    public Long getServiceInfoId() {
        return this.serviceInfoId;
    }

    public void setAppraiseAvg(final Double appraiseAvg) {
        this.appraiseAvg = appraiseAvg;
    }

    public void setAppraiseCount(final Long appraiseCount) {
        this.appraiseCount = appraiseCount;
    }

    public void setOrderCount(final Long orderCount) {
        this.orderCount = orderCount;
    }

    public void setServiceInfo(final ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public void setServiceInfoId(final Long serviceInfoId) {
        this.serviceInfoId = serviceInfoId;
    }

}

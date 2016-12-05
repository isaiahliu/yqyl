//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_order_operation database table.
 *
 */
@Entity
@Table(name = "service_order_operation")
@NamedQuery(name = "ServiceOrderOperation.findAll", query = "SELECT s FROM ServiceOrderOperation s")
public class ServiceOrderOperation extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceOrderOperation_PK_IdGenerator")
    @TableGenerator(name = "ServiceOrderOperation_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceOrderOperation_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
    private Long id;

    private OrderOperation operation;

    private String operator;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String params;

    private RecordStatus status;

    // bi-directional many-to-one association to ServiceOrder
    @ManyToOne
    @JoinColumn(name = "service_order_id")
    private ServiceOrder serviceOrder;

    public ServiceOrderOperation() {
    }

    public Long getId() {
        return this.id;
    }

    public OrderOperation getOperation() {
        return this.operation;
    }

    public String getOperator() {
        return this.operator;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public String getParams() {
        return this.params;
    }

    public ServiceOrder getServiceOrder() {
        return this.serviceOrder;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setOperation(final OrderOperation operation) {
        this.operation = operation;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setParams(final String params) {
        this.params = params;
    }

    public void setServiceOrder(final ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

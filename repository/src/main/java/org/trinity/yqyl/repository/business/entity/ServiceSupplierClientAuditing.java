//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;

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
import org.trinity.yqyl.common.message.lookup.AuditingType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_supplier_client_auditing database table.
 *
 */
@Entity
@Table(name = "service_supplier_client_auditing")
@NamedQuery(name = "ServiceSupplierClientAuditing.findAll", query = "SELECT s FROM ServiceSupplierClientAuditing s")
public class ServiceSupplierClientAuditing extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceSupplierClientAuditing_PK_IdGenerator")
    @TableGenerator(name = "ServiceSupplierClientAuditing_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceSupplierClientAuditing_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
    private Long id;

    private String comment;

    private String operator;

    private RecordStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private AuditingType type;

    // bi-directional many-to-one association to ServiceSupplierClient
    @ManyToOne
    @JoinColumn(name = "service_supplier_client_id")
    private ServiceSupplierClient serviceSupplierClient;

    public ServiceSupplierClientAuditing() {
    }

    public String getComment() {
        return this.comment;
    }

    public Long getId() {
        return this.id;
    }

    public String getOperator() {
        return this.operator;
    }

    public ServiceSupplierClient getServiceSupplierClient() {
        return this.serviceSupplierClient;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public AuditingType getType() {
        return this.type;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
        this.serviceSupplierClient = serviceSupplierClient;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final AuditingType type) {
        this.type = type;
    }
}

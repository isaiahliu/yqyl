//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;

/**
 * The persistent class for the system_attribute database table.
 *
 */
@Entity
@Table(name = "system_attribute")
@NamedQuery(name = "SystemAttribute.findAll", query = "SELECT s FROM SystemAttribute s")
public class SystemAttribute extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String format;

    @Column(name = "`key`")
    private SystemAttributeKey key;

    private RecordStatus status;

    private String value;

    @Column(name = "value_type")
    private String valueType;

    public SystemAttribute() {
    }

    public String getFormat() {
        return this.format;
    }

    public Long getId() {
        return this.id;
    }

    public SystemAttributeKey getKey() {
        return this.key;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public String getValue() {
        return this.value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setKey(final SystemAttributeKey key) {
        this.key = key;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setValueType(final String valueType) {
        this.valueType = valueType;
    }

}

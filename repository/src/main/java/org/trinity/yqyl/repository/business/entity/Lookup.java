//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.Language;
import org.trinity.yqyl.common.message.lookup.LookupType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the lookup database table.
 *
 */
@Entity
@NamedQuery(name = "Lookup.findAll", query = "SELECT l FROM Lookup l")
public class Lookup extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LookupType category;

    private String code;

    private String description;

    private Language language;

    private RecordStatus status;

    public Lookup() {
    }

    public LookupType getCategory() {
        return this.category;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getId() {
        return this.id;
    }

    public Language getLanguage() {
        return this.language;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public void setCategory(final LookupType category) {
        this.category = category;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

}

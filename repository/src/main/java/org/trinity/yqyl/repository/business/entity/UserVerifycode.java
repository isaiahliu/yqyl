//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.VerifyCodeType;

/**
 * The persistent class for the user_verifycode database table.
 *
 */
@Entity
@Table(name = "user_verifycode")
@NamedQuery(name = "UserVerifycode.findAll", query = "SELECT u FROM UserVerifycode u")
public class UserVerifycode extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private RecordStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private VerifyCodeType type;

    // bi-directional one-to-one association to User
    @ManyToOne
    private User user;

    public UserVerifycode() {
    }

    public String getCode() {
        return this.code;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public VerifyCodeType getType() {
        return this.type;
    }

    public User getUser() {
        return this.user;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setStatus(final RecordStatus status) {
        this.status = status;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final VerifyCodeType type) {
        this.type = type;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

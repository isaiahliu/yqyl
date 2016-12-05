//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractEntity;
import org.trinity.yqyl.common.message.lookup.TokenStatus;

/**
 * The persistent class for the token database table.
 *
 */
@Entity
@NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t")
public class Token extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "active_timestamp")
    private Date activeTimestamp;

    @Column(name = "device_identity")
    private String deviceIdentity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_active_timestamp")
    private Date lastActiveTimestamp;

    private TokenStatus status;

    private String token;

    // bi-directional many-to-one association to User
    @ManyToOne
    private User user;

    public Token() {
    }

    public Date getActiveTimestamp() {
        return this.activeTimestamp;
    }

    public String getDeviceIdentity() {
        return this.deviceIdentity;
    }

    public Long getId() {
        return this.id;
    }

    public Date getLastActiveTimestamp() {
        return this.lastActiveTimestamp;
    }

    public TokenStatus getStatus() {
        return this.status;
    }

    public String getToken() {
        return this.token;
    }

    public User getUser() {
        return this.user;
    }

    public void setActiveTimestamp(final Date activeTimestamp) {
        this.activeTimestamp = activeTimestamp;
    }

    public void setDeviceIdentity(final String deviceIdentity) {
        this.deviceIdentity = deviceIdentity;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLastActiveTimestamp(final Date lastActiveTimestamp) {
        this.lastActiveTimestamp = lastActiveTimestamp;
    }

    public void setStatus(final TokenStatus status) {
        this.status = status;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}

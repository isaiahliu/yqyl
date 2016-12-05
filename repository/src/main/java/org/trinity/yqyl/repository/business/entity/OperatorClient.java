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
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;

/**
 * The persistent class for the operator_client database table.
 *
 */
@Entity
@Table(name = "operator_client")
@NamedQuery(name = "OperatorClient.findAll", query = "SELECT o FROM OperatorClient o")
public class OperatorClient extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    private OperatorClientStatus status;

    @Column(name = "staff_no")
    private String staffNo;

    private String name;

    // bi-directional many-to-one association to User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OperatorClient() {
    }

    public String getName() {
        return name;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public OperatorClientStatus getStatus() {
        return this.status;
    }

    public User getUser() {
        return this.user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStaffNo(final String staffNo) {
        this.staffNo = staffNo;
    }

    public void setStatus(final OperatorClientStatus status) {
        this.status = status;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

}

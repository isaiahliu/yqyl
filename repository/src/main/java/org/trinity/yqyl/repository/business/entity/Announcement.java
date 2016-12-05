//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.AnnouncementStatus;
import org.trinity.yqyl.common.message.lookup.ClientType;

/**
 * The persistent class for the announcement database table.
 * 
 */
@Entity
@NamedQuery(name = "Announcement.findAll", query = "SELECT a FROM Announcement a")
public class Announcement extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Announcement_PK_IdGenerator")
    @TableGenerator (name = "Announcement_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "Announcement_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_type")
    private ClientType clientType;

    private AnnouncementStatus status;

    // bi-directional many-to-one association to Message
    @ManyToOne
    private Message message;

    public Announcement() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public AnnouncementStatus getStatus() {
        return this.status;
    }

    public void setStatus(AnnouncementStatus status) {
        this.status = status;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}

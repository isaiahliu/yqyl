//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.awt.TrayIcon.MessageType;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.MessageStatus;

/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
public class Message extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Message_PK_IdGenerator")
	@TableGenerator(name = "Message_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "Message_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	private String content;

	private MessageStatus status;

	private MessageType type;

	// bi-directional many-to-one association to Announcement
	@OneToMany(mappedBy = "message")
	private List<Announcement> announcements;

	public Message() {
	}

	public Announcement addAnnouncement(final Announcement announcement) {
		getAnnouncements().add(announcement);
		announcement.setMessage(this);

		return announcement;
	}

	public List<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public String getContent() {
		return this.content;
	}

	public Long getId() {
		return this.id;
	}

	public MessageStatus getStatus() {
		return this.status;
	}

	public MessageType getType() {
		return this.type;
	}

	public Announcement removeAnnouncement(final Announcement announcement) {
		getAnnouncements().remove(announcement);
		announcement.setMessage(null);

		return announcement;
	}

	public void setAnnouncements(final List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setStatus(final MessageStatus status) {
		this.status = status;
	}

	public void setType(final MessageType type) {
		this.type = type;
	}

}

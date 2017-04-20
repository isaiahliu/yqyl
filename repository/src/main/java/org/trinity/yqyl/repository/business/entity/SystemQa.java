//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the system_qa database table.
 *
 */
@Entity
@Table(name = "system_qa")
@NamedQuery(name = "SystemQa.findAll", query = "SELECT s FROM SystemQa s")
public class SystemQa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String answer;

	private String question;

	private int sequence;

	private RecordStatus status;

	public SystemQa() {
	}

	public String getAnswer() {
		return this.answer;
	}

	public Long getId() {
		return this.id;
	}

	public String getQuestion() {
		return this.question;
	}

	public int getSequence() {
		return this.sequence;
	}

	public RecordStatus getStatus() {
		return this.status;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public void setSequence(final int sequence) {
		this.sequence = sequence;
	}

	public void setStatus(final RecordStatus status) {
		this.status = status;
	}

}

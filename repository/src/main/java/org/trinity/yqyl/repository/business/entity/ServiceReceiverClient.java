//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.CredentialType;
import org.trinity.yqyl.common.message.lookup.FamilyRelationship;
import org.trinity.yqyl.common.message.lookup.Gender;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;

/**
 * The persistent class for the service_receiver_client database table.
 *
 */
@Entity
@Table(name = "service_receiver_client")
@NamedQuery(name = "ServiceReceiverClient.findAll", query = "SELECT s FROM ServiceReceiverClient s")
public class ServiceReceiverClient extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ServiceReceiverClient_PK_IdGenerator")
	@TableGenerator(name = "ServiceReceiverClient_PK_IdGenerator", table = "id_table", pkColumnName = "type", pkColumnValue = "ServiceReceiverClient_PK", valueColumnName = "value", initialValue = 1, allocationSize = 1)
	private Long id;

	private String address;

	@Column(name = "cellphone_no")
	private String cellphoneNo;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name = "family_relationship")
	private FamilyRelationship familyRelationship;

	private String email;

	private Gender gender;

	@Column(name = "homephone_no")
	private String homephoneNo;

	@Column(name = "identity_card")
	private String identityCard;

	@Column(name = "identity_card_copy")
	private String identityCardCopy;

	private String nickname;

	private String name;

	private ServiceReceiverClientStatus status;

	@Column(name = "credential_type")
	private CredentialType credentialType;

	private String region;

	@Column(name = "emergency_contact")
	private String emergencyContact;

	@Column(name = "emergency_contact_no")
	private String emergencyContactNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "registry_date")
	private Date registryDate;

	// bi-directional one-to-one association to
	// ServiceReceiverClientHealthInformation
	@OneToOne(mappedBy = "serviceReceiverClient")
	private ServiceReceiverClientHealthInformation healthInformation;

	// bi-directional one-to-one association to ServiceReceiverClientInterest
	@OneToOne(mappedBy = "serviceReceiverClient")
	private ServiceReceiverClientInterest interest;

	// bi-directional one-to-one association to ServiceReceiverClientOther
	@OneToOne(mappedBy = "serviceReceiverClient")
	private ServiceReceiverClientOther other;

	// bi-directional many-to-one association to Favorite
	@OneToMany(mappedBy = "serviceReceiverClient")
	private List<Favorite> favorites;

	// bi-directional many-to-one association to Yiquan
	@ManyToOne
	@JoinColumn(name = "yiquan_id")
	private Yiquan yiquan;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public ServiceReceiverClient() {
	}

	public Favorite addFavorite(final Favorite favorite) {
		getFavorites().add(favorite);
		favorite.setServiceReceiverClient(this);

		return favorite;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCellphoneNo() {
		return this.cellphoneNo;
	}

	public CredentialType getCredentialType() {
		return credentialType;
	}

	public Date getDob() {
		return this.dob;
	}

	public String getEmail() {
		return this.email;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

	public FamilyRelationship getFamilyRelationship() {
		return familyRelationship;
	}

	public List<Favorite> getFavorites() {
		return this.favorites;
	}

	public Gender getGender() {
		return this.gender;
	}

	public ServiceReceiverClientHealthInformation getHealthInformation() {
		return this.healthInformation;
	}

	public String getHomephoneNo() {
		return this.homephoneNo;
	}

	public Long getId() {
		return this.id;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public String getIdentityCardCopy() {
		return identityCardCopy;
	}

	public ServiceReceiverClientInterest getInterest() {
		return this.interest;
	}

	public String getName() {
		return this.name;
	}

	public String getNickname() {
		return nickname;
	}

	public ServiceReceiverClientOther getOther() {
		return this.other;
	}

	public String getRegion() {
		return region;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public ServiceReceiverClientStatus getStatus() {
		return this.status;
	}

	public User getUser() {
		return this.user;
	}

	public Yiquan getYiquan() {
		return this.yiquan;
	}

	public Favorite removeFavorite(final Favorite favorite) {
		getFavorites().remove(favorite);
		favorite.setServiceReceiverClient(null);

		return favorite;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setCellphoneNo(final String cellphoneNo) {
		this.cellphoneNo = cellphoneNo;
	}

	public void setCredentialType(final CredentialType credentialType) {
		this.credentialType = credentialType;
	}

	public void setDob(final Date dob) {
		this.dob = dob;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEmergencyContact(final String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public void setEmergencyContactNo(final String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}

	public void setFamilyRelationship(final FamilyRelationship familyRelationship) {
		this.familyRelationship = familyRelationship;
	}

	public void setFavorites(final List<Favorite> favorites) {
		this.favorites = favorites;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public void setHealthInformation(final ServiceReceiverClientHealthInformation healthInformation) {
		this.healthInformation = healthInformation;
	}

	public void setHomephoneNo(final String homephoneNo) {
		this.homephoneNo = homephoneNo;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setIdentityCard(final String identityCard) {
		this.identityCard = identityCard;
	}

	public void setIdentityCardCopy(final String identityCardCopy) {
		this.identityCardCopy = identityCardCopy;
	}

	public void setInterest(final ServiceReceiverClientInterest interest) {
		this.interest = interest;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public void setOther(final ServiceReceiverClientOther other) {
		this.other = other;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public void setRegistryDate(final Date registryDate) {
		this.registryDate = registryDate;
	}

	public void setStatus(final ServiceReceiverClientStatus status) {
		this.status = status;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setYiquan(final Yiquan yiquan) {
		this.yiquan = yiquan;
	}
}

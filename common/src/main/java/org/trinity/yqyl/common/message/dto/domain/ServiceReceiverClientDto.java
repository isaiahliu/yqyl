package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.validator.RegexMatchCheck;
import org.trinity.common.dto.washer.KeepAfterWashed;
import org.trinity.yqyl.common.scenario.IScenario.IRealname;
import org.trinity.yqyl.common.validation.IValidationMessage;

public class ServiceReceiverClientDto extends AbstractBusinessDto {

    private String address;

    private String cellphoneNo;

    private Date dob;

    private String email;

    private LookupDto gender;

    private LookupDto credentialType;

    private String homephoneNo;

    @RegexMatchCheck(groups = IRealname.class, regex = "[0-9]{17}[0-9X]{1}", message = IValidationMessage.INVALID_IDENTITY_CARD_FORMAT)
    private String identityCard;

    private String identityCardCopy;

    @NotEmpty(groups = IRealname.class)
    private String name;

    private String nickname;

    private LookupDto familyRelationship;

    private YiquanDto yiquan;

    private String region;

    private String emergencyContact;

    private String emergencyContactNo;

    private Date registryDate;

    private ServiceReceiverClientHealthInformationDto healthInformation;

    private ServiceReceiverClientInterestDto interest;

    private ServiceReceiverClientOtherDto other;

    public String getAddress() {
        return address;
    }

    public String getCellphoneNo() {
        return cellphoneNo;
    }

    public LookupDto getCredentialType() {
        return credentialType;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public LookupDto getFamilyRelationship() {
        return familyRelationship;
    }

    public LookupDto getGender() {
        return gender;
    }

    public ServiceReceiverClientHealthInformationDto getHealthInformation() {
        return healthInformation;
    }

    public String getHomephoneNo() {
        return homephoneNo;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getIdentityCardCopy() {
        return identityCardCopy;
    }

    public ServiceReceiverClientInterestDto getInterest() {
        return interest;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public ServiceReceiverClientOtherDto getOther() {
        return other;
    }

    public String getRegion() {
        return region;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public YiquanDto getYiquan() {
        return yiquan;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCellphoneNo(final String cellphoneNo) {
        this.cellphoneNo = cellphoneNo;
    }

    @KeepAfterWashed(IRealname.class)
    public void setCredentialType(final LookupDto credentialType) {
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

    public void setFamilyRelationship(final LookupDto familyRelationship) {
        this.familyRelationship = familyRelationship;
    }

    public void setGender(final LookupDto gender) {
        this.gender = gender;
    }

    public void setHealthInformation(final ServiceReceiverClientHealthInformationDto healthInformation) {
        this.healthInformation = healthInformation;
    }

    public void setHomephoneNo(final String homephoneNo) {
        this.homephoneNo = homephoneNo;
    }

    @KeepAfterWashed(IRealname.class)
    public void setIdentityCard(final String identityCard) {
        this.identityCard = identityCard;
    }

    @KeepAfterWashed(IRealname.class)
    public void setIdentityCardCopy(final String identityCardCopy) {
        this.identityCardCopy = identityCardCopy;
    }

    public void setInterest(final ServiceReceiverClientInterestDto interest) {
        this.interest = interest;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void setOther(final ServiceReceiverClientOtherDto other) {
        this.other = other;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public void setRegistryDate(final Date registryDate) {
        this.registryDate = registryDate;
    }

    public void setYiquan(final YiquanDto yiquan) {
        this.yiquan = yiquan;
    }
}

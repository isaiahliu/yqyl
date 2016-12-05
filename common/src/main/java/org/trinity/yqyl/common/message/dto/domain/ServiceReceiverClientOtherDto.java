package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceReceiverClientOtherDto extends AbstractBusinessDto {
    private String nationality;

    private String education;

    private String faith;

    private String placeOfBirth;

    private String other;

    public String getEducation() {
        return education;
    }

    public String getFaith() {
        return faith;
    }

    public String getNationality() {
        return nationality;
    }

    public String getOther() {
        return other;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setEducation(final String education) {
        this.education = education;
    }

    public void setFaith(final String faith) {
        this.faith = faith;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public void setOther(final String other) {
        this.other = other;
    }

    public void setPlaceOfBirth(final String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
}

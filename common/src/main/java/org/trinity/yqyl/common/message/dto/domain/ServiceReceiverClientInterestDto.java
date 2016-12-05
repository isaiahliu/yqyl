package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceReceiverClientInterestDto extends AbstractBusinessDto {
    private String literature;

    private String sport;

    private String otherInterest;

    private String club;

    private String clubActivity;

    private String clubRole;

    private String other;

    public String getClub() {
        return club;
    }

    public String getClubActivity() {
        return clubActivity;
    }

    public String getClubRole() {
        return clubRole;
    }

    public String getLiterature() {
        return literature;
    }

    public String getOther() {
        return other;
    }

    public String getOtherInterest() {
        return otherInterest;
    }

    public String getSport() {
        return sport;
    }

    public void setClub(final String club) {
        this.club = club;
    }

    public void setClubActivity(final String clubActivity) {
        this.clubActivity = clubActivity;
    }

    public void setClubRole(final String clubRole) {
        this.clubRole = clubRole;
    }

    public void setLiterature(final String literature) {
        this.literature = literature;
    }

    public void setOther(final String other) {
        this.other = other;
    }

    public void setOtherInterest(final String otherInterest) {
        this.otherInterest = otherInterest;
    }

    public void setSport(final String sport) {
        this.sport = sport;
    }
}

package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class OperatorClientSearchingDto extends AbstractSearchingDto {
    private String username;
    private String staffNo;
    private String name;

    public String getName() {
        return name;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public String getUsername() {
        return username;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStaffNo(final String staffNo) {
        this.staffNo = staffNo;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}

package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceSupplierClientSearchingDto extends AbstractSearchingDto {
    private Long categoryParent;
    private List<Long> categoryChildren;
    private String name;
    private String username;
    private String address;

    private String fromProposalTime;
    private String toProposalTime;

    public String getAddress() {
        return address;
    }

    public List<Long> getCategoryChildren() {
        if (categoryChildren == null) {
            categoryChildren = new ArrayList<>();
        }
        return categoryChildren;
    }

    public Long getCategoryParent() {
        return categoryParent;
    }

    public String getFromProposalTime() {
        return fromProposalTime;
    }

    public String getName() {
        return name;
    }

    public String getToProposalTime() {
        return toProposalTime;
    }

    public String getUsername() {
        return username;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCategoryChildren(final List<Long> categoryChildren) {
        this.categoryChildren = categoryChildren;
    }

    public void setCategoryParent(final Long categoryParent) {
        this.categoryParent = categoryParent;
    }

    public void setFromProposalTime(final String fromProposalTime) {
        this.fromProposalTime = fromProposalTime;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setToProposalTime(final String toProposalTime) {
        this.toProposalTime = toProposalTime;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}

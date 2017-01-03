package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceOrderRequirementSearchingDto extends AbstractSearchingDto {
    private Long after;

    public Long getAfter() {
        return after;
    }

    public void setAfter(final Long after) {
        this.after = after;
    }
}

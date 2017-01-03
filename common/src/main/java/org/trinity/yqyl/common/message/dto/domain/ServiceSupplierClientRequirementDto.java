package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceSupplierClientRequirementDto extends AbstractBusinessDto {
    private Date lastReadTimestamp;

    public Date getLastReadTimestamp() {
        return lastReadTimestamp;
    }

    public void setLastReadTimestamp(final Date lastReadTimestamp) {
        this.lastReadTimestamp = lastReadTimestamp;
    }
}

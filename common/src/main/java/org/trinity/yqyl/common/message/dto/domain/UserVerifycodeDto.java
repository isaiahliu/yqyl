package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class UserVerifycodeDto extends AbstractBusinessDto {
    private String code;

    private Date timestamp;

    private LookupDto type;

    public String getCode() {
        return code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public LookupDto getType() {
        return type;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final LookupDto type) {
        this.type = type;
    }
}

package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ContentDto extends AbstractBusinessDto {
    private String uuid;
    private byte[] content;

    public byte[] getContent() {
        if (content == null) {
            return new byte[0];
        }
        return content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setContent(final byte[] content) {
        this.content = content;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}

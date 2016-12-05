package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.IDto;
import org.trinity.common.dto.object.LookupDto;

public class TokenDto implements IDto {
    private String token;
    private String username;
    private LookupDto status;

    public LookupDto getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setStatus(final LookupDto status) {
        this.status = status;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}

package org.trinity.yqyl.common.message.dto.request;

import org.trinity.common.dto.request.AbstractRequest;
import org.trinity.common.dto.validator.OnValid;
import org.trinity.common.dto.washer.KeepAfterWashed;
import org.trinity.common.dto.washer.OnWash;
import org.trinity.yqyl.common.message.dto.domain.SecurityDto;

public class AuthenticateRequest extends AbstractRequest {
    private SecurityDto user;

    @OnValid
    @OnWash
    public SecurityDto getUser() {
        return user;
    }

    @KeepAfterWashed
    public void setUser(final SecurityDto user) {
        this.user = user;
    }
}

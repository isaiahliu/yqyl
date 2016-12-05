package org.trinity.yqyl.common.message.dto.request;

import org.hibernate.validator.constraints.NotEmpty;
import org.trinity.common.dto.request.AbstractRequest;

public class TokenRequest extends AbstractRequest {
    @NotEmpty
    private String identity;

    private String originalToken;

    public String getIdentity() {
        return identity;
    }

    public String getOriginalToken() {
        return originalToken;
    }

    public void setIdentity(final String identity) {
        this.identity = identity;
    }

    public void setOriginalToken(final String originalToken) {
        this.originalToken = originalToken;
    }
}

package org.trinity.yqyl.common.message.dto.request;

import org.hibernate.validator.constraints.NotEmpty;
import org.trinity.common.dto.request.AbstractRequest;

public class ChangePasswordRequest extends AbstractRequest {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

    public Long getId() {
        return id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }
}

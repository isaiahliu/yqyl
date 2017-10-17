package org.trinity.yqyl.common.message.dto.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.trinity.common.dto.washer.KeepAfterWashed;
import org.trinity.yqyl.common.scenario.IScenario;
import org.trinity.yqyl.common.scenario.IScenario.IAuthenticate;
import org.trinity.yqyl.common.validation.IValidationMessage;

public class SecurityDto {
    @Length(min = 2, max = 40, groups = { IScenario.IAuthenticate.class, IScenario.IRegister.class }, message = IValidationMessage.LENGTH)
    private String username;

    private String password;

    private boolean servicer = false;

    @Length(min = 11, max = 11, groups = { IScenario.IRegister.class,
            IScenario.IRegisterVerify.class }, message = IValidationMessage.LENGTH)
    private String cellphone;

    @NotNull(groups = { IScenario.IRegister.class })
    private String verifyCode;

    public String getCellphone() {
        return cellphone;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public boolean isServicer() {
        return servicer;
    }

    public void setCellphone(final String cellphone) {
        this.cellphone = cellphone;
    }

    @KeepAfterWashed(IAuthenticate.class)
    public void setPassword(final String password) {
        this.password = password;
    }

    public void setServicer(final boolean servicer) {
        this.servicer = servicer;
    }

    @KeepAfterWashed(IAuthenticate.class)
    public void setUsername(final String username) {
        this.username = username;
    }

    public void setVerifyCode(final String verifyCode) {
        this.verifyCode = verifyCode;
    }
}

package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class YiquanDto extends AbstractBusinessDto {
    private String code;

    private Double topUpAmount;

    private Long serviceReceiverClientId;

    private String yiquanPassword;

    private String yiquanPhoneNo;

    private AccountBalanceDto balance;

    private AccountDto account;

    public AccountDto getAccount() {
        return account;
    }

    public AccountBalanceDto getBalance() {
        return balance;
    }

    public String getCode() {
        return code;
    }

    public Long getServiceReceiverClientId() {
        return serviceReceiverClientId;
    }

    public Double getTopUpAmount() {
        return topUpAmount;
    }

    public String getYiquanPassword() {
        return yiquanPassword;
    }

    public String getYiquanPhoneNo() {
        return yiquanPhoneNo;
    }

    public void setAccount(final AccountDto account) {
        this.account = account;
    }

    public void setBalance(final AccountBalanceDto balance) {
        this.balance = balance;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setServiceReceiverClientId(final Long serviceReceiverClientId) {
        this.serviceReceiverClientId = serviceReceiverClientId;
    }

    public void setTopUpAmount(final Double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public void setYiquanPassword(final String yiquanPassword) {
        this.yiquanPassword = yiquanPassword;
    }

    public void setYiquanPhoneNo(final String yiquanPhoneNo) {
        this.yiquanPhoneNo = yiquanPhoneNo;
    }
}

package org.trinity.yqyl.common.message.dto.domain;

public class PosTxDto {
    private String account;
    private double amount;

    public String getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }
}

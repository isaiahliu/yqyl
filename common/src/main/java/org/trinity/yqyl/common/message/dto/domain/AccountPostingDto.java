package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class AccountPostingDto extends AbstractBusinessDto {
    private Double amount;
    private AccountBalanceDto balance;
    private AccountTransactionDto transaction;
    private YiquanDto yiquan;

    public Double getAmount() {
        return amount;
    }

    public AccountBalanceDto getBalance() {
        return balance;
    }

    public AccountTransactionDto getTransaction() {
        return transaction;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public void setBalance(final AccountBalanceDto balance) {
        this.balance = balance;
    }

    public void setTransaction(final AccountTransactionDto transaction) {
        this.transaction = transaction;
    }

    public YiquanDto getYiquan() {
        return yiquan;
    }

    public void setYiquan(YiquanDto yiquan) {
        this.yiquan = yiquan;
    }
}

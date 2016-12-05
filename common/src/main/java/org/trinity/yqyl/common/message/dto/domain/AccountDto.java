package org.trinity.yqyl.common.message.dto.domain;

import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class AccountDto extends AbstractBusinessDto {
    private List<AccountBalanceDto> balances;

    public List<AccountBalanceDto> getBalances() {
        return balances;
    }

    public void setBalances(List<AccountBalanceDto> balances) {
        this.balances = balances;
    }
}

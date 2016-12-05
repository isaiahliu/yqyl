package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class AccountBalanceDto extends AbstractBusinessDto {
    private Double amount;

    private LookupDto category;

    private List<AccountPostingDto> accountPostings;

    public List<AccountPostingDto> getAccountPostings() {
        if (accountPostings == null) {
            accountPostings = new ArrayList<>();
        }
        return accountPostings;
    }

    public Double getAmount() {
        return amount;
    }

    public LookupDto getCategory() {
        return category;
    }

    public void setAccountPostings(final List<AccountPostingDto> accountPostings) {
        this.accountPostings = accountPostings;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public void setCategory(final LookupDto category) {
        this.category = category;
    }

}

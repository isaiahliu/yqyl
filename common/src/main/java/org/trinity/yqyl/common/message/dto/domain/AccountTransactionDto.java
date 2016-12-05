package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class AccountTransactionDto extends AbstractBusinessDto {
    private String code;
    private Date timestamp;

    private LookupDto type;

    private List<AccountPostingDto> accountPostings;

    public List<AccountPostingDto> getAccountPostings() {
        if (accountPostings == null) {
            accountPostings = new ArrayList<>();
        }
        return accountPostings;
    }

    public String getCode() {
        return code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public LookupDto getType() {
        return type;
    }

    public void setAccountPostings(final List<AccountPostingDto> accountPostings) {
        this.accountPostings = accountPostings;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final LookupDto type) {
        this.type = type;
    }
}

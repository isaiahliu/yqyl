package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class ServiceSupplierClientAccountDto extends AbstractBusinessDto {
    private String account;

    private LookupDto bank;

    private String clientName;

    private LookupDto type;

    public String getAccount() {
        return account;
    }

    public LookupDto getBank() {
        return bank;
    }

    public String getClientName() {
        return clientName;
    }

    public LookupDto getType() {
        return type;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public void setBank(final LookupDto bank) {
        this.bank = bank;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public void setType(final LookupDto type) {
        this.type = type;
    }
}

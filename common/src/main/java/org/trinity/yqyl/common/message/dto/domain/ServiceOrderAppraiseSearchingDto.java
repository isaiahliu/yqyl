package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceOrderAppraiseSearchingDto extends AbstractSearchingDto {
    private Long serviceSupplierClientId;

    public Long getServiceSupplierClientId() {
        return serviceSupplierClientId;
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }
}

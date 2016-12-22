package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceOrderAppraiseSearchingDto extends AbstractSearchingDto {
    private Long serviceSupplierClientId;
    private String uid;

    @Override
    @Deprecated
    public Long getId() {
        return super.getId();
    }

    public Long getServiceSupplierClientId() {
        return serviceSupplierClientId;
    }

    public String getUid() {
        return uid;
    }

    @Override
    @Deprecated
    public void setId(final Long id) {
        super.setId(id);
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }
}

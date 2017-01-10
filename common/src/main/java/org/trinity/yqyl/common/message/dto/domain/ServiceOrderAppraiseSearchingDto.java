package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceOrderAppraiseSearchingDto extends AbstractSearchingDto {
    private Long serviceSupplierClientId;
    private String uid;

    /**
     * 1 : Good; 2 : Medium; 3 : Bad.
     */
    private Integer level;
    private boolean requireTotal = false;

    @Override
    @Deprecated
    public Long getId() {
        return super.getId();
    }

    public Integer getLevel() {
        return level;
    }

    public Long getServiceSupplierClientId() {
        return serviceSupplierClientId;
    }

    public String getUid() {
        return uid;
    }

    public boolean isRequireTotal() {
        return requireTotal;
    }

    @Override
    @Deprecated
    public void setId(final Long id) {
        super.setId(id);
    }

    public void setLevel(final Integer level) {
        this.level = level;
    }

    public void setRequireTotal(final boolean requireTotal) {
        this.requireTotal = requireTotal;
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }
}

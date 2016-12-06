package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceSupplierStaffSearchingDto extends AbstractSearchingDto {
    private String name;
    private Long serviceCategoryId;
    private String code;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setServiceCategoryId(final Long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }
}

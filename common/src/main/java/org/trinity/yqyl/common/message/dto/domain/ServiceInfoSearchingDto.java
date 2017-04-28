package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceInfoSearchingDto extends AbstractSearchingDto {
    private Long serviceSupplierClientId;
    private String name;
    private Long categoryId;
    private Long parentCategoryId;
    private String customSortedBy;
    private String customSortedDirection;

    private String province;
    private String city;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCity() {
        return city;
    }

    public String getCustomSortedBy() {
        return customSortedBy;
    }

    public String getCustomSortedDirection() {
        return customSortedDirection;
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public String getProvince() {
        return province;
    }

    public Long getServiceSupplierClientId() {
        return serviceSupplierClientId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setCustomSortedBy(final String customSortedBy) {
        this.customSortedBy = customSortedBy;
    }

    public void setCustomSortedDirection(final String customSortedDirection) {
        this.customSortedDirection = customSortedDirection;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParentCategoryId(final Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setProvince(final String province) {
        this.province = province;
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }
}

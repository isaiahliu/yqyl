package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class ServiceOrderSearchingDto extends AbstractSearchingDto {
    private String receiverUserName;
    private Long serviceSupplierClientId;
    private String serviceDate;
    private String settledDate;
    private String supplierUserName;
    private String category;
    private String paymentMethod;
    private boolean fetchUnprocessedCount;
    private boolean assigned;
    private String staffNo;
    private String staffName;

    public String getCategory() {
        return category;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public Long getServiceSupplierClientId() {
        return serviceSupplierClientId;
    }

    public String getSettledDate() {
        return settledDate;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public String getSupplierUserName() {
        return supplierUserName;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public boolean isFetchUnprocessedCount() {
        return fetchUnprocessedCount;
    }

    public void setAssigned(final boolean assigned) {
        this.assigned = assigned;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public void setFetchUnprocessedCount(final boolean fetchUnprocessedCount) {
        this.fetchUnprocessedCount = fetchUnprocessedCount;
    }

    public void setPaymentMethod(final String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setReceiverUserName(final String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public void setServiceDate(final String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
        this.serviceSupplierClientId = serviceSupplierClientId;
    }

    public void setSettledDate(final String settledDate) {
        this.settledDate = settledDate;
    }

    public void setStaffName(final String staffName) {
        this.staffName = staffName;
    }

    public void setStaffNo(final String staffNo) {
        this.staffNo = staffNo;
    }

    public void setSupplierUserName(final String supplierUserName) {
        this.supplierUserName = supplierUserName;
    }
}

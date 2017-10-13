package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class ServiceOrderDto extends AbstractBusinessDto {
    private Double price;
    private String uid;
    private Date proposalTime;
    private Date approvalTime;
    private Date settledTime;
    private Date serviceDate;
    private Integer serviceHour;
    private String phone;
    private String address;
    private String receipt;
    private String comment;
    private LookupDto paymentMethod;
    private LookupDto paymentType;
    private AccountTransactionDto paymentTransaction;
    private AccountTransactionDto drawbackTransaction;
    private Double expectedPaymentAmount;
    private Double actualPaymentAmount;
    private LookupDto priceChanged;
    private ServiceInfoDto serviceInfo;
    private ServiceOrderAppraiseDto appraise;
    private ServiceSupplierStaffDto staff;
    private List<ServiceOrderOperationDto> operations;
    private UserDto user;
    private byte[] receiptContent;
    private Date txDate;
    private String txReferenceCode;

    public Double getActualPaymentAmount() {
        return actualPaymentAmount;
    }

    public String getAddress() {
        return address;
    }

    public ServiceOrderAppraiseDto getAppraise() {
        return appraise;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public String getComment() {
        return comment;
    }

    public AccountTransactionDto getDrawbackTransaction() {
        return drawbackTransaction;
    }

    public Double getExpectedPaymentAmount() {
        return expectedPaymentAmount;
    }

    /*
     * Use uid instead of id to identify orders
     */
    @Override
    @Deprecated
    public Long getId() {
        return super.getId();
    }

    public List<ServiceOrderOperationDto> getOperations() {
        if (operations == null) {
            operations = new ArrayList<>();
        }
        return operations;
    }

    public LookupDto getPaymentMethod() {
        return paymentMethod;
    }

    public AccountTransactionDto getPaymentTransaction() {
        return paymentTransaction;
    }

    public LookupDto getPaymentType() {
        return paymentType;
    }

    public String getPhone() {
        return phone;
    }

    public Double getPrice() {
        return price;
    }

    public LookupDto getPriceChanged() {
        return priceChanged;
    }

    public Date getProposalTime() {
        return proposalTime;
    }

    public String getReceipt() {
        return receipt;
    }

    public byte[] getReceiptContent() {
        if (receiptContent == null) {
            receiptContent = new byte[0];
        }
        return receiptContent;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public Integer getServiceHour() {
        return serviceHour;
    }

    public ServiceInfoDto getServiceInfo() {
        return serviceInfo;
    }

    public Date getSettledTime() {
        return settledTime;
    }

    public ServiceSupplierStaffDto getStaff() {
        return staff;
    }

    public Date getTxDate() {
        return txDate;
    }

    public String getTxReferenceCode() {
        return txReferenceCode;
    }

    public String getUid() {
        return uid;
    }

    public UserDto getUser() {
        return user;
    }

    public void setActualPaymentAmount(final Double actualPaymentAmount) {
        this.actualPaymentAmount = actualPaymentAmount;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAppraise(final ServiceOrderAppraiseDto appraise) {
        this.appraise = appraise;
    }

    public void setApprovalTime(final Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setDrawbackTransaction(final AccountTransactionDto drawbackTransaction) {
        this.drawbackTransaction = drawbackTransaction;
    }

    public void setExpectedPaymentAmount(final Double expectedPaymentAmount) {
        this.expectedPaymentAmount = expectedPaymentAmount;
    }

    /*
     * Use uid instead of id to identify orders
     */
    @Override
    @Deprecated
    public void setId(final Long id) {
        super.setId(id);
    }

    public void setOperations(List<ServiceOrderOperationDto> operations) {
        if (operations == null) {
            operations = new ArrayList<>();
        }
        this.operations = operations;
    }

    public void setPaymentMethod(final LookupDto paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentTransaction(final AccountTransactionDto paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public void setPaymentType(final LookupDto paymentType) {
        this.paymentType = paymentType;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public void setPriceChanged(final LookupDto priceChanged) {
        this.priceChanged = priceChanged;
    }

    public void setProposalTime(final Date proposalTime) {
        this.proposalTime = proposalTime;
    }

    public void setReceipt(final String receipt) {
        this.receipt = receipt;
    }

    public void setReceiptContent(final byte[] receiptContent) {
        this.receiptContent = receiptContent;
    }

    public void setServiceDate(final Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setServiceHour(final Integer serviceHour) {
        this.serviceHour = serviceHour;
    }

    public void setServiceInfo(final ServiceInfoDto serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public void setSettledTime(final Date settledTime) {
        this.settledTime = settledTime;
    }

    public void setStaff(final ServiceSupplierStaffDto staff) {
        this.staff = staff;
    }

    public void setTxDate(final Date txDate) {
        this.txDate = txDate;
    }

    public void setTxReferenceCode(final String txReferenceCode) {
        this.txReferenceCode = txReferenceCode;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public void setUser(final UserDto user) {
        this.user = user;
    }

}

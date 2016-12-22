package org.trinity.yqyl.common.message.dto.domain;

public class PaymentDto {
    private String orderUid;
    private String yiquanCode;
    private String yiquanPassword;

    public String getYiquanCode() {
        return yiquanCode;
    }

    public String getYiquanPassword() {
        return yiquanPassword;
    }

    public void setYiquanCode(final String yiquanCode) {
        this.yiquanCode = yiquanCode;
    }

    public void setYiquanPassword(final String yiquanPassword) {
        this.yiquanPassword = yiquanPassword;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }
}

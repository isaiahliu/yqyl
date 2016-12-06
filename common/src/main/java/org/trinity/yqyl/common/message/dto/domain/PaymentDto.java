package org.trinity.yqyl.common.message.dto.domain;

public class PaymentDto {
    private Long orderId;
    private String yiquanCode;
    private String yiquanPassword;

    public Long getOrderId() {
        return orderId;
    }

    public String getYiquanCode() {
        return yiquanCode;
    }

    public String getYiquanPassword() {
        return yiquanPassword;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public void setYiquanCode(final String yiquanCode) {
        this.yiquanCode = yiquanCode;
    }

    public void setYiquanPassword(final String yiquanPassword) {
        this.yiquanPassword = yiquanPassword;
    }
}

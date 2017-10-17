package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;

import org.trinity.common.dto.object.IDto;

public class YiquanTxDto implements IDto {
    private String shopName;

    private Double amount;

    private Date datetime;

    public Double getAmount() {
        return amount;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public void setDatetime(final Date datetime) {
        this.datetime = datetime;
    }

    public void setShopName(final String shopName) {
        this.shopName = shopName;
    }
}

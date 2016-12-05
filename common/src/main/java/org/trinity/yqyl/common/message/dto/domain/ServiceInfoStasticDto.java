package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceInfoStasticDto extends AbstractBusinessDto {
    private Double appraiseAvg;

    private Long appraiseCount;

    private Long orderCount;

    public Double getAppraiseAvg() {
        return appraiseAvg;
    }

    public Long getAppraiseCount() {
        return appraiseCount;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setAppraiseAvg(final Double appraiseAvg) {
        this.appraiseAvg = appraiseAvg;
    }

    public void setAppraiseCount(final Long appraiseCount) {
        this.appraiseCount = appraiseCount;
    }

    public void setOrderCount(final Long orderCount) {
        this.orderCount = orderCount;
    }

}

package org.trinity.yqyl.common.message.dto.domain;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceOrderAppraiseDto extends AbstractBusinessDto {
    private Integer attitudeRate;
    private String comment;
    private String reply;
    private Integer onTimeRate;
    private String uid;
    private Integer qualityRate;

    private Integer staffRate;

    private ServiceOrderDto serviceOrder;

    public Integer getAttitudeRate() {
        return attitudeRate;
    }

    public String getComment() {
        return comment;
    }

    /*
     * Use uid instead of id to identify orders
     */
    @Override
    @Deprecated
    public Long getId() {
        return super.getId();
    }

    public Integer getOnTimeRate() {
        return onTimeRate;
    }

    public Integer getQualityRate() {
        return qualityRate;
    }

    public String getReply() {
        return reply;
    }

    public ServiceOrderDto getServiceOrder() {
        return serviceOrder;
    }

    public Integer getStaffRate() {
        return staffRate;
    }

    public String getUid() {
        return uid;
    }

    public void setAttitudeRate(final Integer attitudeRate) {
        this.attitudeRate = attitudeRate;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    /*
     * Use uid instead of id to identify orders
     */
    @Override
    @Deprecated
    public void setId(final Long id) {
        super.setId(id);
    }

    public void setOnTimeRate(final Integer onTimeRate) {
        this.onTimeRate = onTimeRate;
    }

    public void setQualityRate(final Integer qualityRate) {
        this.qualityRate = qualityRate;
    }

    public void setReply(final String reply) {
        this.reply = reply;
    }

    public void setServiceOrder(final ServiceOrderDto serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public void setStaffRate(final Integer staffRate) {
        this.staffRate = staffRate;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }
}

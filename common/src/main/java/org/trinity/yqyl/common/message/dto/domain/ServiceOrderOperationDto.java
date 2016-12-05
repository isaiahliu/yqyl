package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class ServiceOrderOperationDto extends AbstractBusinessDto {
    private LookupDto operation;

    private String operator;

    private LookupDto orderStatus;

    private Date timestamp;

    private List<String> params;

    public LookupDto getOperation() {
        return operation;
    }

    public String getOperator() {
        return operator;
    }

    public LookupDto getOrderStatus() {
        return orderStatus;
    }

    public List<String> getParams() {
        if (params == null) {
            params = new ArrayList<>();
        }
        return params;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setOperation(final LookupDto operation) {
        this.operation = operation;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public void setOrderStatus(final LookupDto orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setParams(final List<String> params) {
        this.params = params;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

}

package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class ServiceSupplierClientAuditingDto extends AbstractBusinessDto {
    private String comment;

    private String operator;

    private Date timestamp;

    private LookupDto type;

    private ServiceSupplierClientDto serviceSupplierClient;

    public String getComment() {
        return comment;
    }

    public String getOperator() {
        return operator;
    }

    public ServiceSupplierClientDto getServiceSupplierClient() {
        return serviceSupplierClient;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public LookupDto getType() {
        return type;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public void setServiceSupplierClient(final ServiceSupplierClientDto serviceSupplierClient) {
        this.serviceSupplierClient = serviceSupplierClient;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(final LookupDto type) {
        this.type = type;
    }
}

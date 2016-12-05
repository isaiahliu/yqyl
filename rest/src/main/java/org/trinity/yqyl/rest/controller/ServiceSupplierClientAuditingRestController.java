package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierClientAuditingRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientAuditingResponse;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAuditingProcessController;

@RestController
@RequestMapping("/client/supplier/auditing")
public class ServiceSupplierClientAuditingRestController extends
        AbstractApplicationAwareCrudRestController<ServiceSupplierClientAuditingDto, ServiceSupplierClientAuditingSearchingDto, IServiceSupplierClientAuditingProcessController, ServiceSupplierClientAuditingRequest, ServiceSupplierClientAuditingResponse> {

    @Override
    protected ServiceSupplierClientAuditingResponse createResponseInstance() {
        return new ServiceSupplierClientAuditingResponse();
    }
}

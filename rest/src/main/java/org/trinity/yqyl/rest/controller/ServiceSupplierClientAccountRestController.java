package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierClientAccountRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientAccountResponse;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAccountProcessController;

@RestController
@RequestMapping("/client/supplier/account")
public class ServiceSupplierClientAccountRestController extends
        AbstractApplicationAwareCrudRestController<ServiceSupplierClientAccountDto, ServiceSupplierClientAccountSearchingDto, IServiceSupplierClientAccountProcessController, ServiceSupplierClientAccountRequest, ServiceSupplierClientAccountResponse> {

    @Override
    protected ServiceSupplierClientAccountResponse createResponseInstance() {
        return new ServiceSupplierClientAccountResponse();
    }
}

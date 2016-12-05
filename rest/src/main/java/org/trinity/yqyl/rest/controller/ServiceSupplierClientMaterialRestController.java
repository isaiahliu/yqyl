package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierClientMaterialRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientMaterialResponse;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientMaterialProcessController;

@RestController
@RequestMapping("/client/supplier/material")
public class ServiceSupplierClientMaterialRestController extends
        AbstractApplicationAwareCrudRestController<ServiceSupplierClientMaterialDto, ServiceSupplierClientMaterialSearchingDto, IServiceSupplierClientMaterialProcessController, ServiceSupplierClientMaterialRequest, ServiceSupplierClientMaterialResponse> {

    @Override
    protected ServiceSupplierClientMaterialResponse createResponseInstance() {
        return new ServiceSupplierClientMaterialResponse();
    }
}

package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AllowanceSupplierClientRequest;
import org.trinity.yqyl.common.message.dto.response.AllowanceSupplierClientResponse;
import org.trinity.yqyl.process.controller.base.IAllowanceSupplierClientProcessController;

/**
 * @author Isaiah Liu
 *
 */
@RestController
@RequestMapping("/client/allowance")
public class AllowanceSupplierClientRestController extends
        AbstractApplicationAwareCrudRestController<AllowanceSupplierClientDto, AllowanceSupplierClientSearchingDto, IAllowanceSupplierClientProcessController, AllowanceSupplierClientRequest, AllowanceSupplierClientResponse> {

    @Override
    protected AllowanceSupplierClientResponse createResponseInstance() {
        return new AllowanceSupplierClientResponse();
    }
}

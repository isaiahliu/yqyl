package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderOperationRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderOperationResponse;
import org.trinity.yqyl.process.controller.base.IServiceOrderOperationProcessController;

@RestController
@RequestMapping("/user/order/operation")
public class ServiceOrderOperationRestController extends
        AbstractApplicationAwareCrudRestController<ServiceOrderOperationDto, ServiceOrderOperationSearchingDto, IServiceOrderOperationProcessController, ServiceOrderOperationRequest, ServiceOrderOperationResponse> {
    @Override
    protected ServiceOrderOperationResponse createResponseInstance() {
        return new ServiceOrderOperationResponse();
    }
}

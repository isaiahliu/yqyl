package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientInterestRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceReceiverClientInterestResponse;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientInterestProcessController;

@RestController
@RequestMapping("/client/receiver/interest")
public class ServiceReceiverClientInterestRestController extends
        AbstractApplicationAwareCrudRestController<ServiceReceiverClientInterestDto, ServiceReceiverClientInterestSearchingDto, IServiceReceiverClientInterestProcessController, ServiceReceiverClientInterestRequest, ServiceReceiverClientInterestResponse> {

    @Override
    protected ServiceReceiverClientInterestResponse createResponseInstance() {
        return new ServiceReceiverClientInterestResponse();
    }
}

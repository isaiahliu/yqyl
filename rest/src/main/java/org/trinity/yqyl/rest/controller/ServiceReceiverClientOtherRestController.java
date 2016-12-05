package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientOtherDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientOtherSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientOtherRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceReceiverClientOtherResponse;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientOtherProcessController;

@RestController
@RequestMapping("/client/receiver/other")
public class ServiceReceiverClientOtherRestController extends
        AbstractApplicationAwareCrudRestController<ServiceReceiverClientOtherDto, ServiceReceiverClientOtherSearchingDto, IServiceReceiverClientOtherProcessController, ServiceReceiverClientOtherRequest, ServiceReceiverClientOtherResponse> {

    @Override
    protected ServiceReceiverClientOtherResponse createResponseInstance() {
        return new ServiceReceiverClientOtherResponse();
    }
}

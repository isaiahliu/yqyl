package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientHealthInformationRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceReceiverClientHealthInformationResponse;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientHealthInformationProcessController;

@RestController
@RequestMapping("/client/receiver/health")
public class ServiceReceiverClientHealthInformationRestController extends
        AbstractApplicationAwareCrudRestController<ServiceReceiverClientHealthInformationDto, ServiceReceiverClientHealthInformationSearchingDto, IServiceReceiverClientHealthInformationProcessController, ServiceReceiverClientHealthInformationRequest, ServiceReceiverClientHealthInformationResponse> {

    @Override
    protected ServiceReceiverClientHealthInformationResponse createResponseInstance() {
        return new ServiceReceiverClientHealthInformationResponse();
    }
}

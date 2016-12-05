package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequirementRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderRequirementResponse;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;

@RestController
@RequestMapping("/service/requirement")
public class ServiceOrderRequirementRestController extends
        AbstractApplicationAwareCrudRestController<ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementProcessController, ServiceOrderRequirementRequest, ServiceOrderRequirementResponse> {

    @Override
    protected ServiceOrderRequirementResponse createResponseInstance() {
        return new ServiceOrderRequirementResponse();
    }
}

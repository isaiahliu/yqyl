package org.trinity.yqyl.rest.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequirementRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderRequirementResponse;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientProcessController;

@RestController
@RequestMapping("/service/requirement")
public class ServiceOrderRequirementRestController extends
        AbstractApplicationAwareCrudRestController<ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementProcessController, ServiceOrderRequirementRequest, ServiceOrderRequirementResponse> {

    @Autowired
    private IServiceSupplierClientProcessController serviceSupplierClientProcessController;

    @Override
    public ResponseEntity<ServiceOrderRequirementResponse> getAll(final ServiceOrderRequirementSearchingDto request) throws IException {
        final ResponseEntity<ServiceOrderRequirementResponse> response = super.getAll(request);

        final Date lastReadTime = serviceSupplierClientProcessController.getLastReadTime();
        if (lastReadTime != null) {
            response.getBody().addExtraData("lastReadTime", lastReadTime);
        }
        return response;
    }

    @Override
    protected ServiceOrderRequirementResponse createResponseInstance() {
        return new ServiceOrderRequirementResponse();
    }
}

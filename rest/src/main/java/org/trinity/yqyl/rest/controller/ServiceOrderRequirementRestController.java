package org.trinity.yqyl.rest.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequirementRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderRequirementResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientProcessController;

@RestController
@RequestMapping("/service/requirement")
public class ServiceOrderRequirementRestController extends
        AbstractApplicationAwareCrudRestController<ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementProcessController, ServiceOrderRequirementRequest, ServiceOrderRequirementResponse> {

    @Autowired
    private IServiceSupplierClientProcessController serviceSupplierClientProcessController;

    @RequestMapping(value = "/cancel/{entityId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<DefaultResponse> cancelRequirement(@PathVariable("entityId") final Long entityId)
            throws IException {
        getDomainProcessController().cancelRequirement(entityId);

        return createResponseEntity();
    }

    @RequestMapping(value = "/catch/{entityId}", method = RequestMethod.POST)
    @Authorize(checkAncestors = false, value = AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ResponseEntity<DefaultResponse> catchRequirement(@PathVariable("entityId") final Long entityId) throws IException {
        getDomainProcessController().catchRequirement(entityId);

        return createResponseEntity();
    }

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

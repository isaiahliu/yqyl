package org.trinity.yqyl.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceInfoResponse;
import org.trinity.yqyl.process.controller.base.IServiceInfoProcessController;

@RestController
@RequestMapping("/service/info")
public class ServiceInfoRestController extends
        AbstractApplicationAwareCrudRestController<ServiceInfoDto, ServiceInfoSearchingDto, IServiceInfoProcessController, ServiceRequest, ServiceInfoResponse> {

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ServiceInfoResponse> getMe(final ServiceInfoSearchingDto request) throws IException {
        final ServiceInfoResponse response = createResponseInstance();

        final List<ServiceInfoDto> data = getDomainProcessController().getMe(request);

        response.addData(data);

        return createResponseEntity(response);
    }

    @Override
    protected ServiceInfoResponse createResponseInstance() {
        return new ServiceInfoResponse();
    }
}

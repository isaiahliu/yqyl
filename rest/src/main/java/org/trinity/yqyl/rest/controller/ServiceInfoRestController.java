package org.trinity.yqyl.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
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

    @RequestMapping(value = "/image/{entityId}", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<DefaultResponse> addImage(@PathVariable("entityId") final Long entityId) throws IException {
        final DefaultResponse response = new DefaultResponse();

        final String uuid = getDomainProcessController().addImage(entityId);

        response.addData(uuid);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/image/{entityId}/{uuid}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<DefaultResponse> deleteImage(@PathVariable("entityId") final Long entityId,
            @PathVariable("uuid") final String uuid) throws IException {
        getDomainProcessController().deleteImage(entityId, uuid);

        return createResponseEntity();
    }

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

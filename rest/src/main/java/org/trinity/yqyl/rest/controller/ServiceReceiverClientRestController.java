package org.trinity.yqyl.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.dto.validator.OnValid;
import org.trinity.common.dto.washer.OnWash;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceReceiverClientResponse;
import org.trinity.yqyl.common.scenario.IScenario.IRealname;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientProcessController;

@RestController
@RequestMapping("/client/receiver/info")
public class ServiceReceiverClientRestController extends
        AbstractApplicationAwareCrudRestController<ServiceReceiverClientDto, ServiceReceiverClientSearchingDto, IServiceReceiverClientProcessController, ServiceReceiverClientRequest, ServiceReceiverClientResponse> {
    @RequestMapping(value = "/disable/{entityId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<DefaultResponse> disable(@PathVariable("entityId") final Long entityId) throws IException {

        getDomainProcessController().disable(entityId);

        return createResponseEntity();
    }

    @RequestMapping(value = "/realname", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<DefaultResponse> realname(
            @RequestBody @OnValid(IRealname.class) @OnWash(IRealname.class) final ServiceReceiverClientRequest request) throws IException {

        getDomainProcessController().realname(request.getData());

        return createResponseEntity();
    }

    @Override
    public ResponseEntity<DefaultResponse> updateAll(@RequestBody final ServiceReceiverClientRequest request) throws IException {
        request.getData().forEach(item -> {
            item.setStatus(null);
            item.setName(null);
            item.setIdentityCard(null);
            item.setIdentityCardCopy(null);
        });

        return super.updateAll(request);
    }

    @Override
    protected ServiceReceiverClientResponse createResponseInstance() {
        return new ServiceReceiverClientResponse();
    }
}

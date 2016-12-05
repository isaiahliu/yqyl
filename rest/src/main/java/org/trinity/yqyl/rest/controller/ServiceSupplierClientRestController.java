package org.trinity.yqyl.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.object.PagingDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierClientRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientResponse;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientProcessController;

@RestController
@RequestMapping("/client/supplier")
public class ServiceSupplierClientRestController extends
        AbstractApplicationAwareCrudRestController<ServiceSupplierClientDto, ServiceSupplierClientSearchingDto, IServiceSupplierClientProcessController, ServiceSupplierClientRequest, ServiceSupplierClientResponse> {

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<DefaultResponse> audit(@RequestBody final ServiceSupplierClientRequest request) throws IException {
        getDomainProcessController().audit(request.getData());

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/propose", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<DefaultResponse> propose(@RequestBody final ServiceSupplierClientRequest request)
            throws IException {
        getDomainProcessController().propose(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ServiceSupplierClientResponse> publicInfo(final ServiceSupplierClientSearchingDto request)
            throws IException {
        final ServiceSupplierClientResponse response = new ServiceSupplierClientResponse();

        final Page<ServiceSupplierClientDto> data = getDomainProcessController().listPublicInfo(request);

        final PagingDto responsePaging = new PagingDto();
        responsePaging.setPageIndex(data.getNumber());
        responsePaging.setPageSize(data.getSize());
        responsePaging.setPageCount(data.getTotalPages());
        responsePaging.setItemCount(data.getTotalElements());

        response.addData(data.getContent());
        response.getMeta().setPaging(responsePaging);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<ServiceSupplierClientResponse> register() throws IException {
        final ServiceSupplierClientResponse response = createResponseInstance();

        final ServiceSupplierClientDto data = getDomainProcessController().register();

        response.addData(data);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<DefaultResponse> reject(@RequestBody final ServiceSupplierClientRequest request) throws IException {
        getDomainProcessController().audit(request.getData());

        return createResponseEntity(new DefaultResponse());
    }

    @Override
    protected ServiceSupplierClientResponse createResponseInstance() {
        return new ServiceSupplierClientResponse();
    }

}

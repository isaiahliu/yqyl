package org.trinity.yqyl.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierStaffRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierStaffResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IServiceSupplierStaffProcessController;

@RestController
@RequestMapping("/client/supplier/staff")
public class ServiceSupplierStaffRestController extends
        AbstractApplicationAwareCrudRestController<ServiceSupplierStaffDto, ServiceSupplierStaffSearchingDto, IServiceSupplierStaffProcessController, ServiceSupplierStaffRequest, ServiceSupplierStaffResponse> {

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ResponseEntity<ServiceSupplierStaffResponse> getAvailableStaffs(final ServiceSupplierStaffSearchingDto request)
            throws IException {

        final ServiceSupplierStaffResponse response = createResponseInstance();

        final List<ServiceSupplierStaffDto> data = getDomainProcessController().getAvailableStaffs(request);

        response.setData(data);

        return createResponseEntity(response);
    }

    @Override
    protected ServiceSupplierStaffResponse createResponseInstance() {
        return new ServiceSupplierStaffResponse();
    }
}

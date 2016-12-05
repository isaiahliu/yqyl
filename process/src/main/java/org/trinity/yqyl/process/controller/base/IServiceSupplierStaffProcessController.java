package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffSearchingDto;

public interface IServiceSupplierStaffProcessController
        extends ICrudProcessController<ServiceSupplierStaffDto, ServiceSupplierStaffSearchingDto> {

    List<ServiceSupplierStaffDto> getAvailableStaffs(ServiceSupplierStaffSearchingDto request) throws IException;
}

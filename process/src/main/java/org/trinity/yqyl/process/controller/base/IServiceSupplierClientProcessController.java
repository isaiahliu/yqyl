package org.trinity.yqyl.process.controller.base;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientSearchingDto;

public interface IServiceSupplierClientProcessController
		extends ICrudProcessController<ServiceSupplierClientDto, ServiceSupplierClientSearchingDto> {

	void audit(List<ServiceSupplierClientDto> serviceSupplierClientDtos) throws IException;

	Date getLastReadTime() throws IException;

	Page<ServiceSupplierClientDto> listPublicInfo(ServiceSupplierClientSearchingDto request) throws IException;

	void propose(ServiceSupplierClientDto data) throws IException;

	void readRequirements() throws IException;

	ServiceSupplierClientDto register() throws IException;

	void reject(List<ServiceSupplierClientDto> serviceSupplierClientDtos) throws IException;

	Page<ServiceSupplierClientDto> reportSuppliers(ServiceSupplierClientSearchingDto request);
}

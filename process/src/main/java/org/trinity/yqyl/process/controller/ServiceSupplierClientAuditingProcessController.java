package org.trinity.yqyl.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAuditingProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientAuditingRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAuditing;

@Service
public class ServiceSupplierClientAuditingProcessController extends
        AbstractAutowiredCrudProcessController<ServiceSupplierClientAuditing, ServiceSupplierClientAuditingDto, ServiceSupplierClientAuditingSearchingDto, IServiceSupplierClientAuditingRepository>
        implements IServiceSupplierClientAuditingProcessController {
    @Autowired
    private IServiceSupplierClientRepository serviceSupplierClientRepository;

    @Override
    protected void addRelationshipFields(final ServiceSupplierClientAuditing entity, final ServiceSupplierClientAuditingDto dto)
            throws IException {
        entity.setServiceSupplierClient(serviceSupplierClientRepository.findOne(dto.getServiceSupplierClient().getId()));
    }

}

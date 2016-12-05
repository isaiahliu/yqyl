package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientMaterialProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientMaterialRepository;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientMaterial;

@Service
public class ServiceSupplierClientMaterialProcessController extends
        AbstractAutowiredCrudProcessController<ServiceSupplierClientMaterial, ServiceSupplierClientMaterialDto, ServiceSupplierClientMaterialSearchingDto, IServiceSupplierClientMaterialRepository>
        implements IServiceSupplierClientMaterialProcessController {
}

package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAllowanceSupplierClientProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAllowanceSupplierClientRepository;
import org.trinity.yqyl.repository.business.entity.AllowanceSupplierClient;

@Service
public class AllowanceSupplierClientProcessController extends
        AbstractAutowiredCrudProcessController<AllowanceSupplierClient, AllowanceSupplierClientDto, AllowanceSupplierClientSearchingDto, IAllowanceSupplierClientRepository>
        implements IAllowanceSupplierClientProcessController {
}

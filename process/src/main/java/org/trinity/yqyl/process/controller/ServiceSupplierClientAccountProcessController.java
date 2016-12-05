package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAccountProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientAccountRepository;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAccount;

@Service
public class ServiceSupplierClientAccountProcessController extends
        AbstractAutowiredCrudProcessController<ServiceSupplierClientAccount, ServiceSupplierClientAccountDto, ServiceSupplierClientAccountSearchingDto, IServiceSupplierClientAccountRepository>
        implements IServiceSupplierClientAccountProcessController {
}

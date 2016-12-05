package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderOperationProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderOperationRepository;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;

@Service
public class ServiceOrderOperationProcessController extends
        AbstractAutowiredCrudProcessController<ServiceOrderOperation, ServiceOrderOperationDto, ServiceOrderOperationSearchingDto, IServiceOrderOperationRepository>
        implements IServiceOrderOperationProcessController {
}

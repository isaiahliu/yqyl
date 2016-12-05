package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.OperatorClientDto;
import org.trinity.yqyl.common.message.dto.domain.OperatorClientSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IOperatorClientProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IOperatorClientRepository;
import org.trinity.yqyl.repository.business.entity.OperatorClient;

@Service
public class OperatorClientProcessController extends
        AbstractAutowiredCrudProcessController<OperatorClient, OperatorClientDto, OperatorClientSearchingDto, IOperatorClientRepository>
        implements IOperatorClientProcessController {
}

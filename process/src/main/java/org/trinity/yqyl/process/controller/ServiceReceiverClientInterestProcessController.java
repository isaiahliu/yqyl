package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientInterestSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientInterestProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientInterestRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientInterest;

@Service
public class ServiceReceiverClientInterestProcessController extends
        AbstractAutowiredCrudProcessController<ServiceReceiverClientInterest, ServiceReceiverClientInterestDto, ServiceReceiverClientInterestSearchingDto, IServiceReceiverClientInterestRepository>
        implements IServiceReceiverClientInterestProcessController {
}

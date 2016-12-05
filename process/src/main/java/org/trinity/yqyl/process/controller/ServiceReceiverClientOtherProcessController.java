package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientOtherDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientOtherSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientOtherProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientOtherRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientOther;

@Service
public class ServiceReceiverClientOtherProcessController extends
        AbstractAutowiredCrudProcessController<ServiceReceiverClientOther, ServiceReceiverClientOtherDto, ServiceReceiverClientOtherSearchingDto, IServiceReceiverClientOtherRepository>
        implements IServiceReceiverClientOtherProcessController {
}

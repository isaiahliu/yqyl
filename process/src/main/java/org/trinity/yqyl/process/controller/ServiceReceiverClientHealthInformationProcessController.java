package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientHealthInformationSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientHealthInformationProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientHealthInformationRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientHealthInformation;

@Service
public class ServiceReceiverClientHealthInformationProcessController extends
        AbstractAutowiredCrudProcessController<ServiceReceiverClientHealthInformation, ServiceReceiverClientHealthInformationDto, ServiceReceiverClientHealthInformationSearchingDto, IServiceReceiverClientHealthInformationRepository>
        implements IServiceReceiverClientHealthInformationProcessController {

}

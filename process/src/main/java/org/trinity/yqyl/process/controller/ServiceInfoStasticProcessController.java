package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceInfoStasticProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceInfoStasticRepository;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;

@Service
public class ServiceInfoStasticProcessController
      extends AbstractAutowiredCrudProcessController<ServiceInfoStastic, ServiceInfoStasticDto, ServiceInfoStasticSearchingDto, IServiceInfoStasticRepository>
      implements IServiceInfoStasticProcessController {
}


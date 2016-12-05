package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceInfoStasticRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceInfoStasticResponse;
import org.trinity.yqyl.process.controller.base.IServiceInfoStasticProcessController;

@RestController
@RequestMapping("/*")
public class ServiceInfoStasticRestController extends
      AbstractApplicationAwareCrudRestController<ServiceInfoStasticDto, ServiceInfoStasticSearchingDto, IServiceInfoStasticProcessController, ServiceInfoStasticRequest, ServiceInfoStasticResponse> {

  @Override
  protected ServiceInfoStasticResponse createResponseInstance() {
      return new ServiceInfoStasticResponse();
  }
}


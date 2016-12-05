package org.trinity.yqyl.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderAppraiseRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderAppraiseResponse;
import org.trinity.yqyl.process.controller.base.IServiceOrderAppraiseProcessController;

@RestController
@RequestMapping("/user/order/appraise")
public class ServiceOrderAppraiseRestController extends
		AbstractApplicationAwareCrudRestController<ServiceOrderAppraiseDto, ServiceOrderAppraiseSearchingDto, IServiceOrderAppraiseProcessController, ServiceOrderAppraiseRequest, ServiceOrderAppraiseResponse> {

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<DefaultResponse> reply(@RequestBody final ServiceOrderAppraiseRequest request) throws IException {
		getDomainProcessController().reply(request.getData());

		return createResponseEntity(new DefaultResponse());
	}

	@Override
	protected ServiceOrderAppraiseResponse createResponseInstance() {
		return new ServiceOrderAppraiseResponse();
	}
}

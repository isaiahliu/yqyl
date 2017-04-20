package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.SystemQaDto;
import org.trinity.yqyl.common.message.dto.domain.SystemQaSearchingDto;
import org.trinity.yqyl.common.message.dto.request.SystemQaRequest;
import org.trinity.yqyl.common.message.dto.response.SystemQaResponse;
import org.trinity.yqyl.process.controller.base.ISystemQaProcessController;

@RestController
@RequestMapping("/qa")
public class SystemQaRestController extends
		AbstractApplicationAwareCrudRestController<SystemQaDto, SystemQaSearchingDto, ISystemQaProcessController, SystemQaRequest, SystemQaResponse> {
	@Override
	protected SystemQaResponse createResponseInstance() {
		return new SystemQaResponse();
	}
}

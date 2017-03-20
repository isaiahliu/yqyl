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
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IServiceOrderAppraiseProcessController;

@RestController
@RequestMapping("/user/order/appraise")
public class ServiceOrderAppraiseRestController extends
		AbstractApplicationAwareCrudRestController<ServiceOrderAppraiseDto, ServiceOrderAppraiseSearchingDto, IServiceOrderAppraiseProcessController, ServiceOrderAppraiseRequest, ServiceOrderAppraiseResponse> {

	@Override
	public ResponseEntity<ServiceOrderAppraiseResponse> getAll(final ServiceOrderAppraiseSearchingDto request) throws IException {
		final ResponseEntity<ServiceOrderAppraiseResponse> responseEntity = super.getAll(request);

		if (request.isRequireTotal()) {
			responseEntity.getBody().addExtraData("totalAppraise",
					getDomainProcessController().countAppraises(request.getServiceSupplierClientId()));
			responseEntity.getBody().addExtraData("level1",
					getDomainProcessController().countAppraisesForRate(request.getServiceSupplierClientId(), 17, 20));
			responseEntity.getBody().addExtraData("level2",
					getDomainProcessController().countAppraisesForRate(request.getServiceSupplierClientId(), 12, 16));
			responseEntity.getBody().addExtraData("level3",
					getDomainProcessController().countAppraisesForRate(request.getServiceSupplierClientId(), 4, 11));
		}
		return responseEntity;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<DefaultResponse> reply(@RequestBody final ServiceOrderAppraiseRequest request) throws IException {
		getDomainProcessController().reply(request.getData());

		return createResponseEntity(new DefaultResponse());
	}

	@Override
	protected ServiceOrderAppraiseResponse createResponseInstance() {
		return new ServiceOrderAppraiseResponse();

	}

	@Override
	protected void validateUpdate() throws IException {
		getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
	}

}

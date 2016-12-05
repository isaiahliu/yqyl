package org.trinity.yqyl.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;
import org.trinity.yqyl.common.message.dto.request.OrderRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IServiceOrderProcessController;

@RestController
@RequestMapping("/user/order")
public class ServiceOrderRestController extends
		AbstractApplicationAwareCrudRestController<ServiceOrderDto, ServiceOrderSearchingDto, IServiceOrderProcessController, OrderRequest, ServiceOrderResponse> {

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ServiceOrderResponse> cancelOrder(@RequestBody final ServiceOrderRequest request)
			throws IException {
		final ServiceOrderResponse response = new ServiceOrderResponse();
		response.addData(getDomainProcessController().cancelOrder(request.getData()));

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/price", method = RequestMethod.PUT)
	@Authorize(AccessRight.SERVICE_SUPPLIER)
	public @ResponseBody ResponseEntity<ServiceOrderResponse> changePrice(@RequestBody final ServiceOrderRequest request)
			throws IException {
		final ServiceOrderResponse response = new ServiceOrderResponse();
		response.addData(getDomainProcessController().changePrice(request.getData()));

		return createResponseEntity(response);
	}

	@Override
	public ResponseEntity<ServiceOrderResponse> getAll(final ServiceOrderSearchingDto request) throws IException {
		final ResponseEntity<ServiceOrderResponse> response = super.getAll(request);

		if (request.isFetchUnprocessedCount()) {
			response.getBody().addExtraData("unprocessedOrderCount",
					getDomainProcessController().countUnprocessedOrders(request.getCurrentUsername()));
		}

		return response;
	}

	@RequestMapping(value = "/proposal", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<ServiceOrderResponse> proposeOrder(@RequestBody final ServiceOrderRequest request)
			throws IException {
		final ServiceOrderResponse response = new ServiceOrderResponse();
		final ServiceOrderDto serviceOrderDto = request.getData().get(0);
		response.addData(getDomainProcessController().proposeOrder(serviceOrderDto));

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/rejectCancel", method = RequestMethod.POST)
	@Authorize(AccessRight.SERVICE_SUPPLIER)
	public @ResponseBody ResponseEntity<ServiceOrderResponse> rejectCancelOrder(@RequestBody final ServiceOrderRequest request)
			throws IException {
		final ServiceOrderResponse response = new ServiceOrderResponse();
		response.addData(getDomainProcessController().rejectCancelOrder(request.getData()));

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/release", method = RequestMethod.POST)
	@Authorize(AccessRight.SERVICE_SUPPLIER)
	public @ResponseBody ResponseEntity<DefaultResponse> releaseOrder(@RequestBody final ServiceOrderRequest request) throws IException {
		getDomainProcessController().releaseOrder(request.getData());

		return createResponseEntity(new DefaultResponse());
	}

	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	@Authorize(AccessRight.SERVICE_SUPPLIER)
	public @ResponseBody ResponseEntity<ServiceOrderResponse> sendTxCode(@RequestBody final ServiceOrderRequest request) throws IException {
		final ServiceOrderResponse response = new ServiceOrderResponse();
		response.addData(getDomainProcessController().sendTxCode(request.getData()));

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/receipt", method = RequestMethod.PUT)
	@Authorize(AccessRight.SERVICE_SUPPLIER)
	public @ResponseBody ResponseEntity<DefaultResponse> uploadReceipt(@RequestBody final ServiceOrderRequest request) throws IException {
		final DefaultResponse response = new DefaultResponse();
		final ServiceOrderDto serviceOrderDto = request.getData().get(0);
		response.addData(getDomainProcessController().uploadReceipt(serviceOrderDto));

		return createResponseEntity(response);
	}

	@Override
	protected ServiceOrderResponse createResponseInstance() {
		return new ServiceOrderResponse();
	}

}

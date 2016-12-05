package org.trinity.yqyl.web.controller.ajax.user;

import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderRequirementRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderRequirementResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/user/order")
public class OrderAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public @ResponseBody DefaultResponse ajaxAssignOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest) throws IException {
        serviceOrderRequest.getData().forEach(item -> {
            item.setStatus(new LookupDto(OrderStatus.IN_PROGRESS));
            item.setApprovalTime(new Date());

            ServiceOrderOperationDto operation = new ServiceOrderOperationDto();
            operation.setOperation(new LookupDto(OrderOperation.TAKEN));

            item.getOperations().add(operation);

            operation = new ServiceOrderOperationDto();
            operation.setOperation(new LookupDto(OrderOperation.ASSIGNMENT));
            operation.getParams().add(item.getStaff().getName());
            operation.getParams().add(item.getStaff().getPhoneNo());

            item.getOperations().add(operation);

            operation = new ServiceOrderOperationDto();
            operation.setOperation(new LookupDto(OrderOperation.PROCESSING));
            operation.getParams().add(item.getStaff().getName());

            item.getOperations().add(operation);
        });

        return restfulServiceUtil.callRestService(Url.ORDER_UPDATE, null, serviceOrderRequest, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public @ResponseBody ServiceOrderResponse ajaxCancelOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest)
            throws IException {

        return restfulServiceUtil.callRestService(Url.ORDER_CANCEL, null, serviceOrderRequest, null, ServiceOrderResponse.class);
    }

    @RequestMapping(value = "/price", method = RequestMethod.PUT)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ServiceOrderResponse ajaxChangePriceOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest)
            throws IException {

        return restfulServiceUtil.callRestService(Url.ORDER_PRICE, null, serviceOrderRequest, null, ServiceOrderResponse.class);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public @ResponseBody DefaultResponse ajaxEditOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest) throws IException {
        return restfulServiceUtil.callRestService(Url.ORDER_UPDATE, null, serviceOrderRequest, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public ResponseEntity<ServiceOrderResponse> ajaxGetOrderDetail(@PathVariable("entityId") final Long entityId,
            final ServiceOrderSearchingDto request) throws IException {
        final ServiceOrderResponse response = restfulServiceUtil.callRestService(Url.ORDER, String.valueOf(entityId), null, request,
                ServiceOrderResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ServiceOrderResponse> ajaxGetOrders(final ServiceOrderSearchingDto request) throws IException {
        final ServiceOrderResponse response = restfulServiceUtil.callRestService(Url.ORDER, null, null, request,
                ServiceOrderResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public @ResponseBody DefaultResponse ajaxPayOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest) throws IException {
        serviceOrderRequest.getData().forEach(item -> item.setStatus(new LookupDto(OrderStatus.AWAITING_APPRAISE)));
        return restfulServiceUtil.callRestService(Url.ORDER_UPDATE, null, serviceOrderRequest, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/proposal", method = { RequestMethod.POST, RequestMethod.PUT })
    public @ResponseBody ServiceOrderResponse ajaxProposeOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest)
            throws IException {
        return restfulServiceUtil.callRestService(Url.ORDER_PROPOSAL, null, serviceOrderRequest, null, ServiceOrderResponse.class);
    }

    @RequestMapping(value = "/requirement", method = RequestMethod.POST)
    public @ResponseBody ServiceOrderRequirementResponse ajaxPublishRequirement(@RequestBody final ServiceOrderRequirementRequest request)
            throws IException {
        request.getData().forEach(item -> {
            item.setId(null);
            item.setStatus(new LookupDto(ServiceOrderRequirementStatus.ACTIVE));
            item.setAnnounceTime(new Date());
            item.setUser(null);
        });

        return restfulServiceUtil.callRestService(Url.REQUIREMENT_NEW, null, request, null, ServiceOrderRequirementResponse.class);
    }

    @RequestMapping(value = "/rejectCancel", method = RequestMethod.POST)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ServiceOrderResponse ajaxRejectCancelOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest)
            throws IException {

        return restfulServiceUtil.callRestService(Url.ORDER_REJECT_CANCEL, null, serviceOrderRequest, null, ServiceOrderResponse.class);
    }

    @RequestMapping(value = "/release", method = RequestMethod.POST)
    public @ResponseBody DefaultResponse ajaxReleaseOrder(@RequestBody final ServiceOrderRequest serviceOrderRequest) throws IException {
        return restfulServiceUtil.callRestService(Url.ORDER_RELEASE, null, serviceOrderRequest, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public @ResponseBody ServiceOrderResponse ajaxSendTxCode(@RequestBody final ServiceOrderRequest serviceOrderRequest) throws IException {
        return restfulServiceUtil.callRestService(Url.ORDER_TRANSACTION, null, serviceOrderRequest, null, ServiceOrderResponse.class);
    }

    @RequestMapping(value = "/{entityId}/receipt", method = RequestMethod.POST)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public ResponseEntity<DefaultResponse> ajaxUploadReceipt(@PathVariable("entityId") final Long entityId,
            final MultipartHttpServletRequest request) throws IException {
        if (request.getFileNames().hasNext()) {
            try {
                final ServiceOrderRequest contentRequest = new ServiceOrderRequest();

                final InputStream stream = request.getFile("IMAGE").getInputStream();
                final byte[] bytes = new byte[stream.available()];
                stream.read(bytes);

                final ServiceOrderDto dto = new ServiceOrderDto();
                dto.setId(entityId);
                dto.setReceiptContent(bytes);
                contentRequest.getData().add(dto);

                return createResponseEntity(
                        restfulServiceUtil.callRestService(Url.ORDER_RECEIPT, null, contentRequest, null, DefaultResponse.class));
            } catch (final Exception e) {
            }
        }

        return createResponseEntity(new DefaultResponse());
    }
}

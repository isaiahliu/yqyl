package org.trinity.yqyl.web.controller.ajax.user;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientHealthInformationRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientInterestRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientOtherRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceReceiverClientRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceReceiverClientResponse;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/user/receiver")
public class ServiceReceiverClientAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ServiceReceiverClientResponse> ajaxAddServiceReceiver(@RequestBody final ServiceReceiverClientRequest request)
            throws IException {
        final ServiceReceiverClientResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_ADD, null, request, null,
                ServiceReceiverClientResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/disable/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity<DefaultResponse> ajaxDisableServiceReceiver(@PathVariable("entityId") final Long entityId) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_DISABLE, String.valueOf(entityId), null, null,
                DefaultResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ServiceReceiverClientResponse> ajaxGetServiceReceiverClient(final ServiceReceiverClientSearchingDto request)
            throws IException {
        final ServiceReceiverClientResponse response = restfulServiceUtil.callRestService(Url.RECEIVER, null, null, request,
                ServiceReceiverClientResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public ResponseEntity<ServiceReceiverClientResponse> ajaxGetUserinfo(@PathVariable("entityId") final Long entityId) throws IException {
        final ServiceReceiverClientResponse response = restfulServiceUtil.callRestService(Url.RECEIVER, String.valueOf(entityId), null,
                null, ServiceReceiverClientResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/realname", method = RequestMethod.POST)
    public ResponseEntity<ServiceReceiverClientResponse> ajaxServiceReceiverRealname(
            @RequestBody final ServiceReceiverClientRequest request) throws IException {
        final ServiceReceiverClientResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_REALNAME, null, request, null,
                ServiceReceiverClientResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUpdateServiceReceiver(@RequestBody final ServiceReceiverClientRequest request)
            throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_UPDATE, null, request, null,
                DefaultResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/health", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUpdateServiceReceiverHealth(
            @RequestBody final ServiceReceiverClientHealthInformationRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_HEALTH_UPDATE, null, request, null,
                DefaultResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/interest", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUpdateServiceReceiverInterest(
            @RequestBody final ServiceReceiverClientInterestRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_INTEREST_UPDATE, null, request, null,
                DefaultResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/other", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUpdateServiceReceiverOther(@RequestBody final ServiceReceiverClientOtherRequest request)
            throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.RECEIVER_OTHER_UPDATE, null, request, null,
                DefaultResponse.class);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/{entityId}/upload", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxUploadIdentityCard(@PathVariable("entityId") final Long entityId,
            final MultipartHttpServletRequest request) throws IException {

        final DefaultResponse response = new DefaultResponse();
        if (request.getFileNames().hasNext()) {
            try {
                final ServiceReceiverClientSearchingDto searchingDto = new ServiceReceiverClientSearchingDto();
                searchingDto.setId(entityId);
                final ServiceReceiverClientResponse receiver = restfulServiceUtil.callRestService(Url.RECEIVER, null, null, searchingDto,
                        ServiceReceiverClientResponse.class);

                final String uuid = receiver.getData().get(0).getIdentityCardCopy();

                final ContentRequest contentRequest = new ContentRequest();

                final InputStream stream = request.getFile("IDENTITY_CARD_COPY").getInputStream();
                final byte[] bytes = new byte[stream.available()];
                stream.read(bytes);

                final ContentDto dto = new ContentDto();
                dto.setUuid(uuid);
                dto.setContent(bytes);
                contentRequest.getData().add(dto);

                restfulServiceUtil.callRestService(Url.CONTENT_UPLOAD, null, contentRequest, null, ContentResponse.class);
            } catch (final Exception e) {
            }
        }

        return createResponseEntity(response);
    }
}

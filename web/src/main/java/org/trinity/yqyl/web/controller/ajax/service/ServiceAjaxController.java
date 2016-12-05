package org.trinity.yqyl.web.controller.ajax.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceInfoRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceInfoResponse;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/service")
public class ServiceAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ServiceInfoResponse ajaxCreateServiceInfo(@RequestBody final ServiceInfoRequest request) throws IException {
        request.getData().forEach(item -> {
            item.setImage(null);
        });

        return restfulServiceUtil.callRestService(Url.SERVICE_INFO_NEW, null, request, null, ServiceInfoResponse.class);
    }

    @RequestMapping(value = "/{entityId}/{uuid}", method = RequestMethod.DELETE)
    public ResponseEntity<DefaultResponse> ajaxDeleteImage(@PathVariable("entityId") final Long entityId,
            @PathVariable("uuid") final String uuid) throws IException {
        restfulServiceUtil.callRestService(Url.SERVICE_INFO_IMAGE_DELETE, String.join("/", String.valueOf(entityId), uuid), null, null,
                ContentResponse.class);

        return createResponseEntity();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody ServiceInfoResponse ajaxGetServiceInfo(final ServiceInfoSearchingDto dto) throws IException {
        return restfulServiceUtil.callRestService(Url.SERVICE_INFO, null, null, dto, ServiceInfoResponse.class);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody ServiceInfoResponse ajaxGetServiceInfoMe(final ServiceInfoSearchingDto dto) throws IException {
        return restfulServiceUtil.callRestService(Url.SERVICE_INFO_ME, null, null, dto, ServiceInfoResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public @ResponseBody ServiceInfoResponse ajaxUpdateServiceInfo(@RequestBody final ServiceInfoRequest request) throws IException {
        request.getData().forEach(item -> {
            if (item.getStatus() != null) {
                if (!ServiceStatus.ACTIVE.getMessageCode().equals(item.getStatus().getCode())) {
                    item.getStatus().setCode(ServiceStatus.OFFLINE.getMessageCode());
                }
            }
        });
        return restfulServiceUtil.callRestService(Url.SERVICE_INFO_UPDATE, null, request, null, ServiceInfoResponse.class);
    }

    @RequestMapping(value = "/{entityId}/upload", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxUploadImage(@PathVariable("entityId") final Long entityId,
            final MultipartHttpServletRequest request) throws IException {

        final DefaultResponse response = new DefaultResponse();
        if (request.getFileNames().hasNext()) {
            try {
                final ServiceInfoSearchingDto searchingDto = new ServiceInfoSearchingDto();
                searchingDto.setId(entityId);
                final DefaultResponse serviceSupplierClientResponse = restfulServiceUtil.callRestService(Url.SERVICE_INFO_IMAGE_ADD,
                        String.valueOf(entityId), null, null, DefaultResponse.class);

                final String uuid = serviceSupplierClientResponse.getData().get(0);

                final ContentRequest contentRequest = new ContentRequest();

                final InputStream stream = request.getFile("IMAGE").getInputStream();
                final byte[] bytes = new byte[stream.available()];
                stream.read(bytes);

                final ContentDto dto = new ContentDto();
                dto.setUuid(uuid);
                dto.setContent(bytes);
                contentRequest.getData().add(dto);

                restfulServiceUtil.callRestService(Url.CONTENT_UPLOAD, null, contentRequest, null, ContentResponse.class);

                response.addData(uuid);
            } catch (final Exception e) {
            }
        }

        return createResponseEntity(response);
    }
}

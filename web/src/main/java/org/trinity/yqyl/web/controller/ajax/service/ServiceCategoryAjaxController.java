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
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategorySearchingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceCategoryRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceCategoryResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/service/category")
public class ServiceCategoryAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody ServiceCategoryResponse ajaxGetAllCategories(final ServiceCategorySearchingDto request) throws IException {
        return restfulServiceUtil.callRestService(Url.SERVICE_CATEGORY, null, null, request, ServiceCategoryResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public @ResponseBody DefaultResponse ajaxUpdateCategories(@RequestBody final ServiceCategoryRequest request) throws IException {
        request.getData().forEach(item -> {
            item.setDescription(null);
            if (item.getId() == null || item.getId() == 0) {
                item.setStatus(new LookupDto(RecordStatus.ACTIVE));
            }
        });

        return restfulServiceUtil.callRestService(Url.SERVICE_CATEGORY_UPDATE, null, request, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/{entityId}/upload", method = RequestMethod.POST)
    @Authorize(AccessRight.ADMINISTRATOR)
    public ResponseEntity<DefaultResponse> ajaxUploadImage(@PathVariable("entityId") final Long entityId,
            final MultipartHttpServletRequest request) throws IException {

        final DefaultResponse response = new DefaultResponse();
        if (request.getFileNames().hasNext()) {
            try {
                final ServiceInfoSearchingDto searchingDto = new ServiceInfoSearchingDto();
                searchingDto.setId(entityId);
                final ServiceCategoryResponse serviceCategoryResponse = restfulServiceUtil.callRestService(Url.SERVICE_CATEGORY,
                        String.valueOf(entityId), null, null, ServiceCategoryResponse.class);

                final String uuid = serviceCategoryResponse.getData().get(0).getImage();

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

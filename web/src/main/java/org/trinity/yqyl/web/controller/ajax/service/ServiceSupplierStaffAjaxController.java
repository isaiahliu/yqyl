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
import org.trinity.common.dto.object.ISearchingDto;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierStaffRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierStaffResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.StaffStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/service/supplier/staff")
public class ServiceSupplierStaffAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "/{entityId}/upload", method = RequestMethod.POST)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public ResponseEntity<DefaultResponse> ajaxChangePassword(@PathVariable("entityId") final Long entityId,
            final MultipartHttpServletRequest request) throws IException {

        final DefaultResponse response = new DefaultResponse();
        if (request.getFileNames().hasNext()) {
            try {
                final ServiceSupplierStaffSearchingDto searchingDto = new ServiceSupplierStaffSearchingDto();
                searchingDto.setId(entityId);
                final ServiceSupplierStaffResponse serviceSupplierStaffResponse = restfulServiceUtil.callRestService(Url.STAFF, null, null,
                        searchingDto, ServiceSupplierStaffResponse.class);

                final String uuid = serviceSupplierStaffResponse.getData().get(0).getPhoto();

                final ContentRequest contentRequest = new ContentRequest();

                final InputStream stream = request.getFile("IMAGE").getInputStream();
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ServiceSupplierStaffResponse ajaxCreateServiceInfo(@RequestBody final ServiceSupplierStaffRequest request)
            throws IException {
        request.getData().forEach(item -> {
            item.setPhoto(null);
            item.setStatus(new LookupDto(StaffStatus.ACTIVE));
        });

        return restfulServiceUtil.callRestService(Url.STAFF_NEW, null, request, null, ServiceSupplierStaffResponse.class);
    }

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ServiceSupplierStaffResponse ajaxGetMyAvailableStaffs(final ServiceSupplierStaffSearchingDto request)
            throws IException {
        request.setId(null);
        request.setName(null);
        request.setSearchScope(ISearchingDto.SEARCH_ME);
        return restfulServiceUtil.callRestService(Url.STAFF_AVAILABLE, null, null, request, ServiceSupplierStaffResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody ServiceSupplierStaffResponse ajaxGetStaffs(final ServiceSupplierStaffSearchingDto request) throws IException {
        return restfulServiceUtil.callRestService(Url.STAFF, null, null, request, ServiceSupplierStaffResponse.class);
    }

    @RequestMapping(value = "/away/{entityId}", method = RequestMethod.DELETE)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody DefaultResponse ajaxStaffAway(@PathVariable("entityId") final Long entityId) throws IException {
        final ServiceSupplierStaffDto serviceSupplierStaffDto = new ServiceSupplierStaffDto();
        serviceSupplierStaffDto.setId(entityId);
        serviceSupplierStaffDto.setStatus(new LookupDto(StaffStatus.FIRED));

        final ServiceSupplierStaffRequest request = new ServiceSupplierStaffRequest();
        request.getData().add(serviceSupplierStaffDto);

        return restfulServiceUtil.callRestService(Url.STAFF_UPDATE, null, request, null, DefaultResponse.class);
    }

    @RequestMapping(value = "/return/{entityId}", method = RequestMethod.POST)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody DefaultResponse ajaxStaffReturn(@PathVariable("entityId") final Long entityId) throws IException {
        final ServiceSupplierStaffDto serviceSupplierStaffDto = new ServiceSupplierStaffDto();
        serviceSupplierStaffDto.setId(entityId);
        serviceSupplierStaffDto.setStatus(new LookupDto(StaffStatus.ACTIVE));

        final ServiceSupplierStaffRequest request = new ServiceSupplierStaffRequest();
        request.getData().add(serviceSupplierStaffDto);

        return restfulServiceUtil.callRestService(Url.STAFF_UPDATE, null, request, null, DefaultResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public @ResponseBody ServiceSupplierStaffResponse ajaxUpdateStaff(@RequestBody final ServiceSupplierStaffRequest request)
            throws IException {
        request.getData().forEach(item -> {
            item.setPhoto(null);
            item.setStatus(null);
        });
        return restfulServiceUtil.callRestService(Url.STAFF_UPDATE, null, request, null, ServiceSupplierStaffResponse.class);
    }
}

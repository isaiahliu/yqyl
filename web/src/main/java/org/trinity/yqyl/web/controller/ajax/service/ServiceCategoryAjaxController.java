package org.trinity.yqyl.web.controller.ajax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategorySearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceCategoryRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceCategoryResponse;
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
}

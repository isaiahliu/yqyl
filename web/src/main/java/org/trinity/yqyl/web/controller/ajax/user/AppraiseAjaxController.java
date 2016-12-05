package org.trinity.yqyl.web.controller.ajax.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceOrderAppraiseRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderAppraiseResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/user/order/appraise")
public class AppraiseAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "/active/{entityId}", method = RequestMethod.PUT)
    public @ResponseBody DefaultResponse ajaxActiveOrderAppraise(@PathVariable("entityId") final Long entityId) throws IException {
        final ServiceOrderAppraiseDto dto = new ServiceOrderAppraiseDto();
        dto.setId(entityId);
        dto.setStatus(new LookupDto(RecordStatus.ACTIVE));
        final ServiceOrderAppraiseRequest request = new ServiceOrderAppraiseRequest();
        request.getData().add(dto);

        return restfulServiceUtil.callRestService(Url.APPRAISE_UPDATE, null, request, null, DefaultResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ServiceOrderAppraiseResponse ajaxAppraiseOrder(@RequestBody final ServiceOrderAppraiseRequest request)
            throws IException {
        return restfulServiceUtil.callRestService(Url.APPRAISE_NEW, null, request, null, ServiceOrderAppraiseResponse.class);
    }

    @RequestMapping(value = "/disable/{entityId}", method = RequestMethod.PUT)
    public @ResponseBody DefaultResponse ajaxDisableOrderAppraise(@PathVariable("entityId") final Long entityId) throws IException {
        final ServiceOrderAppraiseDto dto = new ServiceOrderAppraiseDto();
        dto.setId(entityId);
        dto.setStatus(new LookupDto(RecordStatus.DISABLED));
        final ServiceOrderAppraiseRequest request = new ServiceOrderAppraiseRequest();
        request.getData().add(dto);

        return restfulServiceUtil.callRestService(Url.APPRAISE_UPDATE, null, request, null, DefaultResponse.class);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody ServiceOrderAppraiseResponse ajaxGetAppraise(final ServiceOrderAppraiseSearchingDto request) throws IException {
        return restfulServiceUtil.callRestService(Url.APPRAISE, null, null, request, ServiceOrderAppraiseResponse.class);
    }

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    @Authorize(AccessRight.SERVICE_SUPPLIER)
    public @ResponseBody DefaultResponse ajaxReplyOrder(@RequestBody final ServiceOrderAppraiseRequest request) throws IException {
        return restfulServiceUtil.callRestService(Url.APPRAISE_REPLY, null, request, null, DefaultResponse.class);
    }

}

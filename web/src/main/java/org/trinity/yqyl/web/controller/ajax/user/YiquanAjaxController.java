package org.trinity.yqyl.web.controller.ajax.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;
import org.trinity.yqyl.common.message.dto.request.YiquanRequest;
import org.trinity.yqyl.common.message.dto.response.YiquanResponse;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/user/yiquan")
public class YiquanAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "/bind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxBindYiquan(@RequestBody final YiquanRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.YIQUAN_BIND, null, request, null, DefaultResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<YiquanResponse> ajaxGetYiquan(final YiquanSearchingDto request) throws IException {
        final YiquanResponse response = restfulServiceUtil.callRestService(Url.YIQUAN, null, null, request, YiquanResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/topup", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxTopUpYiquan(@RequestBody final YiquanRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.YIQUAN_TOPUP, null, request, null, DefaultResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUnbindYiquan(@RequestBody final YiquanRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.YIQUAN_UNBIND, null, request, null, DefaultResponse.class);

        return createResponseEntity(response);
    }
}

package org.trinity.yqyl.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;
import org.trinity.yqyl.common.message.dto.request.YiquanRequest;
import org.trinity.yqyl.common.message.dto.response.YiquanResponse;
import org.trinity.yqyl.process.controller.base.IYiquanProcessController;

@RestController
@RequestMapping("/yiquan")
public class YiquanRestController extends
        AbstractApplicationAwareCrudRestController<YiquanDto, YiquanSearchingDto, IYiquanProcessController, YiquanRequest, YiquanResponse> {

    @RequestMapping(value = "/bind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxBindMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().bindMe(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/topup", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxTopupMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().topup(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUnbindMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().unbindMe(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @Override
    protected YiquanResponse createResponseInstance() {
        return new YiquanResponse();
    }
}

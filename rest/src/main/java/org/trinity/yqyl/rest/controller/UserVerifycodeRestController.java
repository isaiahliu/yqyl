package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeDto;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeSearchingDto;
import org.trinity.yqyl.common.message.dto.request.UserVerifycodeRequest;
import org.trinity.yqyl.common.message.dto.response.UserVerifycodeResponse;
import org.trinity.yqyl.process.controller.base.IUserVerifycodeProcessController;

@RestController
@RequestMapping("/user/verify")
public class UserVerifycodeRestController extends
        AbstractApplicationAwareCrudRestController<UserVerifycodeDto, UserVerifycodeSearchingDto, IUserVerifycodeProcessController, UserVerifycodeRequest, UserVerifycodeResponse> {

    @Override
    protected UserVerifycodeResponse createResponseInstance() {
        return new UserVerifycodeResponse();
    }
}

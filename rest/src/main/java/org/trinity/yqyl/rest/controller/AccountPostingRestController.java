package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AccountPostingRequest;
import org.trinity.yqyl.common.message.dto.response.AccountPostingResponse;
import org.trinity.yqyl.process.controller.base.IAccountPostingProcessController;

@RestController
@RequestMapping("/account/posting")
public class AccountPostingRestController extends
        AbstractApplicationAwareCrudRestController<AccountPostingDto, AccountPostingSearchingDto, IAccountPostingProcessController, AccountPostingRequest, AccountPostingResponse> {
    @Override
    protected AccountPostingResponse createResponseInstance() {
        return new AccountPostingResponse();
    }

    @Override
    protected void validateAdd() throws IException {
        getSecurityUtil().deny();
    }

    @Override
    protected void validateDelete() throws IException {
        getSecurityUtil().deny();
    }

    @Override
    protected void validateUpdate() throws IException {
        getSecurityUtil().deny();
    }
}

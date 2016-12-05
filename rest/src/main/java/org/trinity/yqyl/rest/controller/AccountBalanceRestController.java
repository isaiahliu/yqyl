package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceDto;
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AccountBalanceRequest;
import org.trinity.yqyl.common.message.dto.response.AccountBalanceResponse;
import org.trinity.yqyl.process.controller.base.IAccountBalanceProcessController;

@RestController
@RequestMapping("/account/balance")
public class AccountBalanceRestController extends
        AbstractApplicationAwareCrudRestController<AccountBalanceDto, AccountBalanceSearchingDto, IAccountBalanceProcessController, AccountBalanceRequest, AccountBalanceResponse> {
    @Override
    protected AccountBalanceResponse createResponseInstance() {
        return new AccountBalanceResponse();
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

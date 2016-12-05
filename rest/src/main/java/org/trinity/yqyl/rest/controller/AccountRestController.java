package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.AccountDto;
import org.trinity.yqyl.common.message.dto.domain.AccountSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AccountRequest;
import org.trinity.yqyl.common.message.dto.response.AccountResponse;
import org.trinity.yqyl.process.controller.base.IAccountProcessController;

@RestController
@RequestMapping("/account")
public class AccountRestController extends
		AbstractApplicationAwareCrudRestController<AccountDto, AccountSearchingDto, IAccountProcessController, AccountRequest, AccountResponse> {

	@Override
	protected AccountResponse createResponseInstance() {
		return new AccountResponse();
	}
}

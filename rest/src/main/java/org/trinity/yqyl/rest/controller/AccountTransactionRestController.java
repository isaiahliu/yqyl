package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AccountTransactionRequest;
import org.trinity.yqyl.common.message.dto.response.AccountTransactionResponse;
import org.trinity.yqyl.process.controller.base.IAccountTransactionProcessController;

@RestController
@RequestMapping("/account/transaction")
public class AccountTransactionRestController extends
		AbstractApplicationAwareCrudRestController<AccountTransactionDto, AccountTransactionSearchingDto, IAccountTransactionProcessController, AccountTransactionRequest, AccountTransactionResponse> {

	@Override
	protected AccountTransactionResponse createResponseInstance() {
		return new AccountTransactionResponse();
	}
}

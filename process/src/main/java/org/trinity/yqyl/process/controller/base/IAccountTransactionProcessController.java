package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionSearchingDto;
import org.trinity.yqyl.repository.business.entity.AccountTransaction;

public interface IAccountTransactionProcessController
        extends ICrudProcessController<AccountTransactionDto, AccountTransactionSearchingDto> {
    AccountTransaction processTransaction(AccountTransactionDto transaction) throws IException;
}

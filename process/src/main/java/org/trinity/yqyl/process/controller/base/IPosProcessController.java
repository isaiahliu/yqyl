package org.trinity.yqyl.process.controller.base;

import org.springframework.data.domain.Page;
import org.trinity.common.exception.IException;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalTransactionEnquiryResponse.Transaction;
import org.trinity.process.controller.IProcessController;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.PosTxDto;

public interface IPosProcessController extends IProcessController {
    double getBalance(String account) throws IException;

    PosTxDto getTransaction(final String monthAndDay, final String referenceCode) throws IException;

    Page<Transaction> listTransactions(AccountPostingSearchingDto request) throws IException;

    void payment(String account, String password, double amount) throws IException;

    void verify(String code, String yiquanPassword) throws IException;
}

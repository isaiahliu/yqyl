package org.trinity.yqyl.process.controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccountPostingStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountTransactionProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAccountBalanceRepository;
import org.trinity.yqyl.repository.business.dataaccess.IAccountPostingRepository;
import org.trinity.yqyl.repository.business.dataaccess.IAccountTransactionRepository;
import org.trinity.yqyl.repository.business.entity.AccountBalance;
import org.trinity.yqyl.repository.business.entity.AccountPosting;
import org.trinity.yqyl.repository.business.entity.AccountTransaction;

@Service
public class AccountTransactionProcessController extends
        AbstractAutowiredCrudProcessController<AccountTransaction, AccountTransactionDto, AccountTransactionSearchingDto, IAccountTransactionRepository>
        implements IAccountTransactionProcessController {

    @Autowired
    private IAccountBalanceRepository accountBalanceRepository;

    @Autowired
    private IAccountPostingRepository accountPostingRepository;

    @Autowired
    private IObjectConverter<AccountPosting, AccountPostingDto> accountPostingConverter;

    @Override
    @Transactional(rollbackOn = IException.class)
    public AccountTransactionDto processTransaction(final AccountTransactionDto transactionDto) throws IException {
        final AccountTransaction accountTransaction = getDomainObjectConverter().convertBack(transactionDto);

        accountTransaction.setId(null);
        accountTransaction.setStatus(RecordStatus.ACTIVE);
        accountTransaction.setTimestamp(new Date());

        getDomainEntityRepository().save(accountTransaction);

        for (final AccountPostingDto accountPostingDto : transactionDto.getAccountPostings()) {
            final AccountPosting accountPosting = accountPostingConverter.convertBack(accountPostingDto);

            accountPosting.setId(null);
            accountPosting.setStatus(AccountPostingStatus.ACTIVE);
            accountPosting.setAccountTransaction(accountTransaction);

            final AccountBalance accountBalance = accountBalanceRepository.findOne(accountPostingDto.getBalance().getId());

            final double balance = accountBalance.getAmount() + accountPosting.getAmount();

            if (balance < 0) {
                throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_BALANCE);
            }

            accountBalance.setAmount(balance);
            accountBalanceRepository.save(accountBalance);

            accountPosting.setAccountBalance(accountBalance);

            accountPostingRepository.save(accountPosting);
        }

        return getDomainObjectConverter().convert(accountTransaction);
    }
}

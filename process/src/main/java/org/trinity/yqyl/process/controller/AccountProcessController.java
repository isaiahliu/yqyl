package org.trinity.yqyl.process.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.AccountDto;
import org.trinity.yqyl.common.message.dto.domain.AccountSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccountBalanceStatus;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.AccountStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAccountBalanceRepository;
import org.trinity.yqyl.repository.business.dataaccess.IAccountRepository;
import org.trinity.yqyl.repository.business.entity.Account;
import org.trinity.yqyl.repository.business.entity.AccountBalance;

@Service
public class AccountProcessController
        extends AbstractAutowiredCrudProcessController<Account, AccountDto, AccountSearchingDto, IAccountRepository>
        implements IAccountProcessController {
    @Autowired
    private IAccountBalanceRepository accountBalanceRepository;

    @Override
    public Account createAccount() {
        final Account account = new Account();
        account.setStatus(AccountStatus.ACTIVE);

        getDomainEntityRepository().save(account);

        final AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAmount(0d);
        accountBalance.setCategory(AccountCategory.YIQUAN);
        accountBalance.setStatus(AccountBalanceStatus.ACTIVE);
        accountBalance.setAccount(account);
        accountBalanceRepository.save(accountBalance);

        account.setBalances(new ArrayList<>());
        account.addBalance(accountBalance);
        return account;
    }
}

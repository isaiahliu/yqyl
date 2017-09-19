package org.trinity.yqyl.process.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceDto;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;
import org.trinity.yqyl.common.message.lookup.TransactionType;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountProcessController;
import org.trinity.yqyl.process.controller.base.IAccountTransactionProcessController;
import org.trinity.yqyl.process.controller.base.IPosProcessController;
import org.trinity.yqyl.process.controller.base.IYiquanProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IYiquanRepository;
import org.trinity.yqyl.repository.business.entity.Account;
import org.trinity.yqyl.repository.business.entity.AccountBalance;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient;
import org.trinity.yqyl.repository.business.entity.Yiquan;

@Service
public class YiquanProcessController
        extends AbstractAutowiredCrudProcessController<Yiquan, YiquanDto, YiquanSearchingDto, IYiquanRepository>
        implements IYiquanProcessController {
    @Autowired
    private IServiceReceiverClientRepository serviceReceiverClientRepository;

    @Autowired
    private IAccountProcessController accountProcessController;

    @Autowired
    private IAccountTransactionProcessController accountTransactionProcessController;

    @Autowired
    private IObjectConverter<AccountBalance, AccountBalanceDto> accountBalanceConverter;

    @Autowired
    private IPosProcessController posProcessController;

    @Override
    @Transactional(rollbackOn = IException.class)
    public void bindMe(final YiquanDto yiquanDto) throws IException {
        if (StringUtils.isEmpty(yiquanDto.getCode())) {
            return;
        }

        final String username = getSecurityUtil().getCurrentToken().getUsername();

        final ServiceReceiverClient client = serviceReceiverClientRepository
                .findOne(yiquanDto.getServiceReceiverClientId());

        if (!client.getUser().getUsername().equals(username)) {
            throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
        }

        if (client.getStatus() != ServiceReceiverClientStatus.REALNAME) {
            throw getExceptionFactory().createException(ErrorMessage.CLIENT_SHOULD_BE_REALNAME);
        }

        if (client.getYiquan() != null) {
            throw getExceptionFactory().createException(ErrorMessage.YIQUAN_IS_ALREADY_BINDED);
        }

        // final String code = yiquanDto.getCode();
        // final String name = client.getName();
        // final String password = yiquanDto.getYiquanPassword();
        // final String identityCard = client.getIdentityCard();
        // TODO verify yiquan code, password, name, identity card from API.
        // TODO If verify fails, throw exception.

        client.setYiquan(create(yiquanDto.getCode()));

        serviceReceiverClientRepository.save(client);
    }

    @Override
    public Yiquan create(final String yiquanCode) throws IException {
        Yiquan yiquan = getDomainEntityRepository().findOneByCode(yiquanCode);

        if (yiquan == null) {
            final Account account = accountProcessController.createAccount();
            yiquan = new Yiquan();
            yiquan.setCode(yiquanCode);
            yiquan.setStatus(RecordStatus.ACTIVE);
            yiquan.setAccount(account);
            getDomainEntityRepository().save(yiquan);

            final double yiquanBalance = posProcessController.getBalance(yiquanCode);

            final AccountTransactionDto transaction = new AccountTransactionDto();

            transaction.setType(new LookupDto(TransactionType.BIND));
            final AccountPostingDto accountPostingDto = new AccountPostingDto();
            accountPostingDto.setAmount(yiquanBalance);
            accountPostingDto.setBalance(accountBalanceConverter.convert(account.getBalances().stream()
                    .filter(item -> item.getCategory() == AccountCategory.YIQUAN).findAny().get()));
            transaction.getAccountPostings().add(accountPostingDto);

            accountTransactionProcessController.processTransaction(transaction);
        }
        return yiquan;
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void topup(final YiquanDto yiquanDto) throws IException {
        if (yiquanDto.getTopUpAmount() == null || yiquanDto.getTopUpAmount() <= 0) {
            throw getExceptionFactory().createException(ErrorMessage.TOPUP_AMOUNT_MUST_BE_POSITIVE);
        }

        final Yiquan yiquan = getDomainEntityRepository().findOneByCode(yiquanDto.getCode());

        if (yiquan == null) {
            throw getExceptionFactory().createException(ErrorMessage.INVALID_YIQUAN_CODE);
        }

        final AccountBalance accountBalance = yiquan.getAccount().getBalances().stream()
                .filter(item -> item.getCategory() == AccountCategory.YIQUAN).findFirst().get();

        final AccountTransactionDto transaction = new AccountTransactionDto();

        transaction.setType(new LookupDto(TransactionType.TOP_UP));
        final AccountPostingDto accountPostingDto = new AccountPostingDto();
        accountPostingDto.setAmount(yiquanDto.getTopUpAmount());
        accountPostingDto.setBalance(accountBalanceConverter.convert(accountBalance));
        transaction.getAccountPostings().add(accountPostingDto);

        accountTransactionProcessController.processTransaction(transaction);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void unbindMe(final YiquanDto yiquanDto) throws IException {
        final String username = getSecurityUtil().getCurrentToken().getUsername();

        final ServiceReceiverClient client = serviceReceiverClientRepository
                .findOne(yiquanDto.getServiceReceiverClientId());

        if (!client.getUser().getUsername().equals(username)) {
            throw getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
        }

        if (client.getYiquan() == null) {
            return;
        }

        // final String code = client.getYiquan().getCode();
        // final String name = client.getName();
        // final String password = yiquanDto.getYiquanPassword();
        // final String identityCard = client.getIdentityCard();
        // TODO verify yiquan code, password, name, identity card from API.
        // TODO If verify fails, throw exception.

        client.setYiquan(null);

        serviceReceiverClientRepository.save(client);
    }

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }
}

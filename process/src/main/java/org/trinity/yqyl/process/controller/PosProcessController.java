package org.trinity.yqyl.process.controller;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.framework.contact.tsykt.ITsyktMessage;
import org.trinity.framework.contact.tsykt.TsyktMessagePool;
import org.trinity.framework.contact.tsykt.TsyktMessageSerializer;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalBalanceEnquiryRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalKekDownloadRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalPaymentRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalSignUpRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalTransactionDetailEnquiryRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalTransactionEnquiryRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.response.AbstractTsyktTerminalResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalBalanceEnquiryResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalSignUpResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalTransactionDetailEnquiryResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalTransactionEnquiryResponse;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.message.exception.IErrorMessage;
import org.trinity.yqyl.common.message.dto.domain.PosTxDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.process.controller.base.IPosProcessController;

@Service
public class PosProcessController implements IPosProcessController {
    @Autowired
    private IExceptionFactory exceptionFactory;

    @Value("${args.pos.tsk}")
    private String tsk;

    @Value("${args.pos.shop}")
    private String shopId;

    @Value("${args.pos.terminal}")
    private String terminalId;

    @Value("${args.pos.center.url}")
    private String posCenterUrl;

    @Value("${args.pos.center.port}")
    private int posCenterPort;

    private int serialNo;

    private byte[] kek = null;

    private byte[] mak = null;

    private LocalDate lastUpdateDate = LocalDate.now();

    private final TsyktMessagePool pool = new TsyktMessagePool();

    @Override
    @Transactional(rollbackOn = IException.class)
    public double getBalance(final String account) throws IException {
        signUp();

        final TsyktTerminalBalanceEnquiryRequest query = new TsyktTerminalBalanceEnquiryRequest();
        query.setAccount(account);
        query.setTransactionCode("310000");
        query.setSerialNo(String.valueOf(serialNo++));
        query.setServiceConditionCode("00");
        query.setTerminalCode(terminalId);
        query.setShopCode(shopId);
        query.setTransactionTypeCode(1);
        query.setBatchNo(1);
        query.setManageNo(0);
        query.setAccountCode(account);
        query.setOperatorCode("operator");
        query.setPassword("password");

        final TsyktTerminalBalanceEnquiryResponse queryResponse = (TsyktTerminalBalanceEnquiryResponse) getResponse(
                query);

        return Double.parseDouble(queryResponse.getAmount().substring(16, 26)) / 100;
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public PosTxDto getTransaction(final String monthAndDay, final String referenceCode) throws IException {
        signUp();

        final TsyktTerminalTransactionDetailEnquiryRequest request = new TsyktTerminalTransactionDetailEnquiryRequest();

        request.setAccount("");
        request.setAmount("0");
        request.setField60(11);
        request.setManageNo(0);
        request.setReferenceCode("0");
        request.setServiceConditionCode("00");
        request.setShopCode(shopId);
        request.setTerminalCode(terminalId);
        request.setTransactionCode("957105");
        request.setTransactionTypeCode(0);
        request.setMonthAndDay(monthAndDay);
        request.setReferenceCode(referenceCode);

        final TsyktTerminalTransactionDetailEnquiryResponse response = getResponse(request);

        final PosTxDto dto = new PosTxDto();
        dto.setAccount(response.getAccount());
        dto.setAmount(Double.parseDouble(
                org.springframework.util.StringUtils.isEmpty(response.getTxAmount()) ? "0" : response.getTxAmount())
                / 100);
        return dto;
    }

    @Transactional(rollbackOn = IException.class)
    public Page<String> listTransactions(final String account, final LocalDate startDate, final LocalDate endDate)
            throws IException {
        final TsyktTerminalTransactionEnquiryRequest transactionRequest = new TsyktTerminalTransactionEnquiryRequest();

        transactionRequest.setAccount(account);
        transactionRequest.setTransactionCode("957095");
        transactionRequest.setAmount("0");
        transactionRequest.setSerialNo(String.valueOf(serialNo++));
        transactionRequest.setServiceConditionCode("00");
        transactionRequest.setTerminalCode(terminalId);
        transactionRequest.setShopCode(shopId);
        transactionRequest.setStartDate(startDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        transactionRequest.setEndDate(endDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        transactionRequest.setStartIndex("1");

        final TsyktTerminalTransactionEnquiryResponse response = (TsyktTerminalTransactionEnquiryResponse) getResponse(
                transactionRequest);
        return new PageImpl<>(null, new PageRequest(1, 15), Integer.parseInt(response.getTotalSize()));
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void payment(final String account, final String password, final double amount) throws IException {
        signUp();

        final TsyktTerminalPaymentRequest payment = new TsyktTerminalPaymentRequest();
        payment.setAccount(account);
        payment.setTransactionCode("000000");
        payment.setPassword(password);
        payment.setAmount(String.valueOf((int) (amount * 100)));
        payment.setTerminalCode(terminalId);
        payment.setShopCode(shopId);
        payment.setAccountCode(account);

        payment.setSerialNo(String.valueOf(serialNo++));
        payment.setServiceConditionCode("00");
        payment.setOperatorCode("operator");
        payment.setTransactionTypeCode(22);
        payment.setBatchNo(1);
        payment.setManageNo(0);
        getResponse(payment);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void verify(final String account, final String password) throws IException {
        try {
            payment(account, password, 99999999.99);
        } catch (final IException e) {
            final IErrorMessage messageType = e.getErrorMessages().get(0).getItem1();
            if (messageType != ErrorMessage.INSUFFICIENT_BALANCE
                    && messageType != ErrorMessage.PAYMENT_EXCEEDS_ALLOWED_ALLOWANCE) {
                throw e;
            }
        }
    }

    private byte[] decrypt(final byte[] content, final byte[] key) {
        try {
            final DESKeySpec dks = new DESKeySpec(key);
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final SecretKey securekey = keyFactory.generateSecret(dks);
            final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());

            return cipher.doFinal(content);
        } catch (final Exception e) {
            return new byte[0];
        }
    }

    private byte[] decrypt2(final byte[] content, final byte[] key) {
        try {
            final byte[] firstHalf = new byte[8];
            final byte[] secondHalf = new byte[8];

            System.arraycopy(key, 0, firstHalf, 0, 8);
            System.arraycopy(key, 8, secondHalf, 0, 8);

            final byte[] step1 = decrypt(content, firstHalf);
            final byte[] step2 = encrypt(step1, secondHalf);
            return decrypt(step2, firstHalf);
        } catch (final Exception e) {
            return new byte[0];
        }
    }

    private void downloadKek() throws IException {
        final TsyktTerminalKekDownloadRequest kekDownloadMessage = new TsyktTerminalKekDownloadRequest();

        kekDownloadMessage.setTerminalCode(terminalId);// 终端标识码
        kekDownloadMessage.setShopCode(shopId);// 商户代码
        kekDownloadMessage.setTransactionTypeCode(0);// 交易类型吗
        kekDownloadMessage.setBatchNo(1);// 批次号
        kekDownloadMessage.setManageNo(3800);// 管理信息吗

        final TsyktTerminalSignUpResponse kekResponse = (TsyktTerminalSignUpResponse) getResponse(kekDownloadMessage);

        kek = decrypt2(parseHex(kekResponse.getPik()), parseHex(tsk));
    }

    private byte[] encrypt(final byte[] content, final byte[] key) throws Exception {
        final DESKeySpec dks = new DESKeySpec(key);
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey securekey = keyFactory.generateSecret(dks);
        final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());

        return cipher.doFinal(content);
    }

    private <T extends AbstractTsyktTerminalResponse> T getResponse(final ITsyktMessage request) throws IException {
        final byte[] codesFromMessages = pool.getCodesFromMessages("", request);

        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            try (Socket socket = new Socket(posCenterUrl, posCenterPort)) {
                int i;
                socket.getOutputStream().write(codesFromMessages);
                while ((i = socket.getInputStream().read()) != -1) {
                    byteArray.write(i);
                }
            }

            final List<ITsyktMessage> responses = pool.getMessagesFromCodes("", byteArray.toByteArray());
            @SuppressWarnings("unchecked")
            final T response = (T) responses.get(0);

            switch (response.getResponseCode()) {
            case "B1":
                throw exceptionFactory.createException(ErrorMessage.CANNOT_FIND_TRANSACTION);
            case "C1":
                throw exceptionFactory.createException(ErrorMessage.ACCOUNT_LOCKED);
            case "99":
                throw exceptionFactory.createException(ErrorMessage.WRONG_PASSWORD);
            case "51":
                throw exceptionFactory.createException(ErrorMessage.INSUFFICIENT_BALANCE);
            case "61":
                throw exceptionFactory.createException(ErrorMessage.PAYMENT_EXCEEDS_ALLOWED_ALLOWANCE);
            case "00":
                break;
            default:
                throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION, "请求失败");
            }

            return response;
        } catch (final IException e) {
            throw e;
        } catch (final Exception e) {
            throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
        }
    }

    private byte[] parseHex(final String hex) {
        final byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        return result;
    }

    private void signUp() throws IException {
        if (kek != null && mak != null && lastUpdateDate.compareTo(LocalDate.now()) >= 0) {
            return;
        }

        downloadKek();

        final TsyktTerminalSignUpRequest message = new TsyktTerminalSignUpRequest();

        message.setSerialNo(serialNo++);// 系统跟踪号
        message.setTerminalCode(terminalId);// 终端标识码
        message.setShopCode(shopId);// 商户代码
        message.setTransactionTypeCode(0);// 交易类型吗
        message.setBatchNo(1);// 批次号
        message.setManageNo(10);// 管理信息吗
        message.setOperatorCode("system");// 操作员
        message.setPasswordVerifyCode("N");// 密码校验标识
        message.setPassword("123456");// 密码

        final TsyktTerminalSignUpResponse response = (TsyktTerminalSignUpResponse) getResponse(message);

        mak = decrypt(parseHex(response.getMak()), kek);

        TsyktMessageSerializer.mak = mak;
        lastUpdateDate = LocalDate.now();
    }
}

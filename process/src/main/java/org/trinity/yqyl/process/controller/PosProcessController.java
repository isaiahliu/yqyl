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
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalSignUpRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalTransactionEnquiryRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalBalanceEnquiryResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalSignUpResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalTransactionEnquiryResponse;
import org.trinity.message.exception.GeneralErrorMessage;
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
        query.setSerialNo("310");
        query.setServiceConditionCode("00");
        query.setTerminalCode(terminalId);
        query.setShopCode(shopId);
        query.setTransactionTypeCode(1);
        query.setBatchNo(3);
        query.setManageNo(0);
        query.setAccountCode(account);
        query.setOperatorCode("operator");
        query.setPassword("password");

        final TsyktTerminalBalanceEnquiryResponse queryResponse = (TsyktTerminalBalanceEnquiryResponse) getResponse(
                query);

        return Double.parseDouble(queryResponse.getAmount().substring(16, 26)) / 100;
    }

    public Page<String> listTransactions(final String account, final LocalDate startDate, final LocalDate endDate)
            throws IException {
        final TsyktTerminalTransactionEnquiryRequest transactionRequest = new TsyktTerminalTransactionEnquiryRequest();

        transactionRequest.setAccount(account);
        transactionRequest.setTransactionCode("957095");
        transactionRequest.setAmount("0");
        transactionRequest.setSerialNo("111");
        transactionRequest.setServiceConditionCode("00");
        transactionRequest.setTerminalCode(terminalId);
        transactionRequest.setShopCode(shopId);
        transactionRequest.setStartDate(startDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        transactionRequest.setEndDate(endDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        transactionRequest.setStartIndex(1);

        final TsyktTerminalTransactionEnquiryResponse response = (TsyktTerminalTransactionEnquiryResponse) getResponse(
                transactionRequest);
        return new PageImpl<>(null, new PageRequest(1, 10), 10);
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
        kekDownloadMessage.setBatchNo(3);// 批次号
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

    private ITsyktMessage getResponse(final ITsyktMessage request) throws IException {
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
            return responses.get(0);
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

        message.setSerialNo(112);// 系统跟踪号
        message.setTerminalCode(terminalId);// 终端标识码
        message.setShopCode(shopId);// 商户代码
        message.setTransactionTypeCode(0);// 交易类型吗
        message.setBatchNo(3);// 批次号
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

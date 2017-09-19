package org.trinity.yqyl.process.controller;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.framework.contact.tsykt.ITsyktMessage;
import org.trinity.framework.contact.tsykt.TsyktMessagePool;
import org.trinity.framework.contact.tsykt.TsyktMessageSerializer;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalBalanceEnquiryRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalKekDownloadRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.request.TsyktTerminalSignUpRequest;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalBalanceEnquiryResponse;
import org.trinity.framework.contact.tsykt.messages.terminal.response.TsyktTerminalSignUpResponse;
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

    private String kek = null;

    private String mak = null;

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

        final byte[] codesFromMessages = pool.getCodesFromMessages("", query);

        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            try (Socket socket = new Socket(posCenterUrl, posCenterPort)) {
                int i;
                socket.getOutputStream().write(codesFromMessages);
                while ((i = socket.getInputStream().read()) != -1) {
                    byteArray.write(i);
                }
            }

            final List<ITsyktMessage> responses = pool.getMessagesFromCodes("", byteArray.toByteArray());
            final TsyktTerminalBalanceEnquiryResponse queryResponse = (TsyktTerminalBalanceEnquiryResponse) responses
                    .get(0);

            return Double.parseDouble(queryResponse.getAmount());
        } catch (final Exception e) {
            throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
        }
    }

    private void downloadKek() throws IException {
        final TsyktTerminalKekDownloadRequest kekDownloadMessage = new TsyktTerminalKekDownloadRequest();

        kekDownloadMessage.setTerminalCode("400000000002");// 终端标识码
        kekDownloadMessage.setShopCode("400020000001");// 商户代码
        kekDownloadMessage.setTransactionTypeCode(0);// 交易类型吗
        kekDownloadMessage.setBatchNo(3);// 批次号
        kekDownloadMessage.setManageNo(3800);// 管理信息吗

        final byte[] codesFromMessages = pool.getCodesFromMessages("", kekDownloadMessage);

        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            try (Socket socket = new Socket(posCenterUrl, posCenterPort)) {
                socket.getOutputStream().write(codesFromMessages);
                int i;
                while ((i = socket.getInputStream().read()) != -1) {
                    byteArray.write(i);
                }
            }
            final List<ITsyktMessage> responses = pool.getMessagesFromCodes("", byteArray.toByteArray());
            final TsyktTerminalSignUpResponse kekResponse = (TsyktTerminalSignUpResponse) responses.get(0);

            final DESKeySpec dks = new DESKeySpec(tsk.getBytes());
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final SecretKey securekey = keyFactory.generateSecret(dks);
            final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
            kek = new String(cipher.doFinal(kekResponse.getPik().getBytes()));
        } catch (final Exception e) {
            throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
        }
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

        final byte[] codesFromMessages = pool.getCodesFromMessages("", message);

        try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
            try (Socket socket = new Socket(posCenterUrl, posCenterPort)) {
                socket.getOutputStream().write(codesFromMessages);
                int i;
                while ((i = socket.getInputStream().read()) != -1) {
                    byteArray.write(i);
                }
            } catch (final Exception e) {
                throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
            }

            final byte[] bytes = byteArray.toByteArray();

            final List<ITsyktMessage> responses = pool.getMessagesFromCodes("", bytes);
            final TsyktTerminalSignUpResponse response = (TsyktTerminalSignUpResponse) responses.get(0);

            final DESKeySpec dks = new DESKeySpec(kek.getBytes());
            final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            final SecretKey securekey = keyFactory.generateSecret(dks);
            final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
            mak = new String(cipher.doFinal(response.getMak().getBytes()));
            TsyktMessageSerializer.mak = mak;
            lastUpdateDate = LocalDate.now();
        } catch (final Exception ex) {
            throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
        }
    }
}

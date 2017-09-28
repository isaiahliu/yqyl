package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.IProcessController;
import org.trinity.yqyl.common.message.dto.domain.PosTxDto;

public interface IPosProcessController extends IProcessController {
    double getBalance(String account) throws IException;

    PosTxDto getTransaction(int batchNo, String searchingCode) throws IException;

    void payment(String account, String password, double amount) throws IException;

    void verify(String code, String yiquanPassword) throws IException;
}

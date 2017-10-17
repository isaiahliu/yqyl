package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.IProcessController;

public interface ISmsProcessController extends IProcessController {
    void sendMessage(String cellphone, String verifyCode) throws IException;
}

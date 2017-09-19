package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.IProcessController;

public interface IPosProcessController extends IProcessController {
    double getBalance(String account) throws IException;
}

package org.trinity.yqyl.process.tcp;

import org.trinity.common.exception.IException;
import org.trinity.framework.contact.tsykt.ITsyktMessage;

public interface ITcpProcessController {
	ITsyktMessage process(ITsyktMessage message) throws IException;
}

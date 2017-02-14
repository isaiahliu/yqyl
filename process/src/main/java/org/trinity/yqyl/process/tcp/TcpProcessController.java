package org.trinity.yqyl.process.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.framework.contact.tsykt.ITsyktMessage;
import org.trinity.framework.contact.tsykt.TsyktMessagePool;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.yqyl.common.message.exception.ErrorMessage;

@Service
public class TcpProcessController implements ITcpProcessController {
	private final static String OWNER = "TCP";

	@Autowired
	private IExceptionFactory exceptionFactory;

	@Override
	public ITsyktMessage process(final ITsyktMessage message) throws IException {
		try (Socket socket = new Socket("xxxx", 123)) {
			final TsyktMessagePool pool = new TsyktMessagePool();
			socket.getOutputStream().write(pool.getCodesFromMessages(OWNER, message));

			final byte[] result = new byte[1024];
			socket.getInputStream().read(result);

			final List<ITsyktMessage> messages = pool.getMessagesFromCodes(OWNER, result);

			if (messages == null || messages.size() == 0) {
				throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
			}
			return messages.get(0);
		} catch (final IOException e) {
			throw exceptionFactory.createException(ErrorMessage.UNABLE_TO_CONNECT_YKT);
		}
	}

}

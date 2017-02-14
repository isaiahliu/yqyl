package org.trinity.yqyl.process.tcp;

import org.apache.mina.api.AbstractIoHandler;
import org.apache.mina.api.IoSession;
import org.apache.mina.session.AttributeKey;
import org.trinity.framework.contact.tsykt.ITsyktMessageHandler;
import org.trinity.framework.contact.tsykt.ITsyktMessageSession;

public class TsyktIoHandler extends AbstractIoHandler {

	private static final AttributeKey<TsyktMessageSession> CONTACT_SESSION = new AttributeKey<>(TsyktMessageSession.class,
			"CONTACT_SESSION");

	private final ITsyktMessageHandler messageHandler;

	public TsyktIoHandler(final ITsyktMessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	@Override
	public void messageReceived(final IoSession session, final Object message) {

		super.messageReceived(session, message);

		final String owner = session.getRemoteAddress().toString();
		messageHandler.onReceive(owner, (ITsyktMessageSession) session.getAttribute(CONTACT_SESSION), (byte[]) message);
	}

	@Override
	public void messageSent(final IoSession session, final Object message) {
		super.messageSent(session, message);
	}

	@Override
	public void sessionOpened(final IoSession session) {
		super.sessionOpened(session);

		session.setAttribute(CONTACT_SESSION, new TsyktMessageSession(session));
	}

}

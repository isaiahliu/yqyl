package org.trinity.yqyl.process.tcp;

import org.apache.mina.api.IoSession;
import org.apache.mina.session.AttributeKey;
import org.trinity.framework.contact.tsykt.ITsyktMessageSession;

public class TsyktMessageSession implements ITsyktMessageSession {
    private static enum AttributeKeyEnum {
        TIMESTAMP;

        public <T> T getValue(final IoSession session, final T defaultValue, final Class<T> clazz) {
            return session.getAttribute(new AttributeKey<>(clazz, name()), defaultValue);
        }

        public <T> void storeValue(final IoSession session, final T value, final Class<T> clazz) {
            session.setAttribute(new AttributeKey<>(clazz, name()), value);
        }

    }

    private boolean active;

    private IoSession session;

    public TsyktMessageSession(final IoSession session) {
        this();
        this.session = session;
    }

    private TsyktMessageSession() {
        active = true;
    }

    @Override
    public void close() {
        active = false;
    }

    @Override
    public String getMessageSessionKey() {
        return "";
    }

    @Override
    public long getTimestamp() {
        return AttributeKeyEnum.TIMESTAMP.getValue(session, 0l, Long.class);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void sendMessage(final byte[] message) {
        session.write(message);
    }

    @Override
    public void setTimestamp(final long timestamp) {
        AttributeKeyEnum.TIMESTAMP.storeValue(session, timestamp, Long.class);
    }

}

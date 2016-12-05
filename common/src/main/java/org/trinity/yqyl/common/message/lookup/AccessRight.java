package org.trinity.yqyl.common.message.lookup;

import org.trinity.common.accessright.IAccessRight;

public enum AccessRight implements IAccessRight<LookupType> {
    DISABLED("DISABLED", null),

    SUPER_USER("SUPER", null),

    ADMINISTRATOR("ADMIN", SUPER_USER),

    SERVICE_SUPPLIER("SUPPLIER", SUPER_USER),

    SERVICE_SUPPLIER_REGISTER("SUP_REG", SERVICE_SUPPLIER),

    OPERATOR("OPERATOR", SUPER_USER);

    private final String messageCode;
    private final AccessRight parentAccessRight;

    private AccessRight(final String messageCode, final AccessRight parentAccessRight) {
        this.messageCode = messageCode;
        this.parentAccessRight = parentAccessRight;
    }

    @Override
    public String getAuthority() {
        return messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ACCESS_RIGHT;
    }

    @Override
    public AccessRight getParentAccessRight() {
        return parentAccessRight;
    }
}

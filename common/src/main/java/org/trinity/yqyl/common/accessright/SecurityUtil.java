package org.trinity.yqyl.common.accessright;

import org.trinity.common.accessright.AbstractSecurityUtil;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.message.IMessageResolverChain;
import org.trinity.message.LookupParser;
import org.trinity.yqyl.common.message.lookup.AccessRight;

public class SecurityUtil extends AbstractSecurityUtil<AccessRight> {

    public SecurityUtil(final IMessageResolverChain messageResolver, final IExceptionFactory exceptionFactory) {
        super(messageResolver, exceptionFactory);
    }

    @Override
    protected AccessRight getAccessRightByName(final String accessRightName) {
        return LookupParser.parse(AccessRight.class, accessRightName);
    }
}

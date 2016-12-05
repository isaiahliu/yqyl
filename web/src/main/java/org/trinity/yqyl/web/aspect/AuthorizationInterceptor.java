package org.trinity.yqyl.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@Component
@Aspect
public class AuthorizationInterceptor {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @Pointcut(value = "@annotation(org.trinity.yqyl.common.accessright.Authorize)")
    public void authorizationPointCut() {
    }

    @Before("authorizationPointCut() && @annotation(authorize)")
    public void authorize(final JoinPoint joinPoint, final Authorize authorize) throws IException {
        securityUtil.checkAccessRight(authorize.value(), authorize.checkAncestors());
    }
}

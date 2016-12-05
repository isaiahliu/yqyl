package org.trinity.yqyl.rest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.accessright.AuthToken;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.ISecurityProcessController;

@Component
@Aspect
public class RequestInterceptor {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @Autowired
    private IExceptionFactory exceptionFactory;

    @Autowired
    private ISecurityProcessController securityProcessController;

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void authorizationPointCut() {
    }

    @Before("authorizationPointCut()")
    public void before(final JoinPoint joinPoint) throws IException, InstantiationException, IllegalAccessException {
        if (!securityUtil.hasToken()) {
            return;
        }

        final AuthToken token = securityUtil.getCurrentToken();

        switch (token.getStatus()) {
        case TOKEN_IS_EXPIRED:
            securityProcessController.logout(token.getToken());
            throw exceptionFactory.createException(ErrorMessage.TOKEN_IS_EXPIRED);
        case LOGGED_BY_OTHERS:
            securityProcessController.logout(token.getToken());
            throw exceptionFactory.createException(ErrorMessage.LOGGED_BY_OTHERS);
        case PASSWORD_CHANGED:
            securityProcessController.logout(token.getToken());
            throw exceptionFactory.createException(ErrorMessage.PASSWORD_CHANGED);
        case USER_IS_DISABLED:
            securityProcessController.logout(token.getToken());
            throw exceptionFactory.createException(ErrorMessage.USER_IS_DISABLED);
        default:
            break;
        }
    }
}

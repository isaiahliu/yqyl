package org.trinity.yqyl.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.trinity.common.accessright.AuthToken;
import org.trinity.common.accessright.TokenAuthenticationStatus;
import org.trinity.common.exception.IException;
import org.trinity.rest.security.AbstractPreAuthenticationFilter;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.response.TokenResponse;

public class SessionFilter extends AbstractPreAuthenticationFilter {
    private static final String RESOURCE_PATH = "/static/";
    public static final String COOKIE_NAME = "YQYL_SESSION_ID";

    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected AuthToken getToken(final String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        try {
            final Map<String, String> map = new HashMap<>();
            map.put("tokenName", token);

            final TokenResponse tokenResponse = restfulServiceUtil.callRestService(null, Url.TOKEN_VERIFY, null, null, map,
                    TokenResponse.class);

            final AuthToken result = new AuthToken(token);
            result.setStatus(TokenAuthenticationStatus.AUTHENTICATED);
            result.setUsername(tokenResponse.getData().get(0).getUsername());
            result.setUserDetailKey(token);
            return result;
        } catch (final IException e) {
            return null;
        }
    }

    @Override
    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Override
    protected boolean isResourcePath(final String path) {
        return path.toLowerCase().startsWith(RESOURCE_PATH);
    }

    @Override
    protected String parseTokenName(final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}

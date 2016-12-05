package org.trinity.yqyl.web.controller.ajax.user;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.AbstractResponse;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.SecurityDto;
import org.trinity.yqyl.common.message.dto.domain.UserSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AuthenticateRequest;
import org.trinity.yqyl.common.message.dto.request.ChangePasswordRequest;
import org.trinity.yqyl.common.message.dto.request.TokenRequest;
import org.trinity.yqyl.common.message.dto.request.UserRequest;
import org.trinity.yqyl.common.message.dto.response.SecurityResponse;
import org.trinity.yqyl.common.message.dto.response.TokenResponse;
import org.trinity.yqyl.common.message.dto.response.UserResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.web.util.SessionFilter;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/user")
public class UserAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxChangePassword(@RequestBody final ChangePasswordRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.USER_CHANGE_PASSWORD, null, request, null,
                DefaultResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @Authorize(AccessRight.ADMINISTRATOR)
    public @ResponseBody UserResponse ajaxGetServiceInfoMe(final UserSearchingDto dto) throws IException {
        return restfulServiceUtil.callRestService(Url.USER, null, null, dto, UserResponse.class);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> ajaxGetUserinfo() throws IException {
        final UserResponse response = restfulServiceUtil.callRestService(Url.USER_ME, null, null, null, UserResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.PUT)
    public ResponseEntity<AbstractResponse<?>> ajaxLogin(@RequestBody final SecurityDto dto, final HttpServletResponse httpResponse)
            throws IException {
        final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
        authenticateRequest.setUser(dto);
        final TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setIdentity(UUID.randomUUID().toString());
        final TokenResponse tokenRespose = restfulServiceUtil.callRestService(Url.TOKEN_NEW, null, tokenRequest, null, TokenResponse.class);

        if (!tokenRespose.getErrors().isEmpty()) {
            return createResponseEntity(tokenRespose);
        }

        final String newToken = tokenRespose.getData().get(0).getToken();

        final SecurityResponse securityResponse = restfulServiceUtil.callRestService(newToken, Url.AUTHENTICATE, null, authenticateRequest,
                null, SecurityResponse.class);

        if (securityResponse.getErrors().isEmpty()) {
            final Cookie cookie = new Cookie(SessionFilter.COOKIE_NAME, newToken);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            return createResponseEntity(tokenRespose);
        }

        return createResponseEntity(securityResponse);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.PUT)
    public ResponseEntity<SecurityResponse> ajaxLogout(final HttpServletResponse httpResponse) throws IException {
        final SecurityResponse response = restfulServiceUtil.callRestService(Url.LOGOUT, null, null, null, SecurityResponse.class);

        if (response.getErrors().isEmpty()) {
            final Cookie cookie = new Cookie(SessionFilter.COOKIE_NAME, "");
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
        }

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxRegister(@RequestBody final SecurityDto dto) throws IException {
        final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
        authenticateRequest.setUser(dto);

        final DefaultResponse response = restfulServiceUtil.callRestService(Url.REGISTER, null, authenticateRequest, null,
                DefaultResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/registerVerify", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxRegisterVerify(@RequestBody final SecurityDto dto) throws IException {
        final AuthenticateRequest authenticateRequest = new AuthenticateRequest();
        authenticateRequest.setUser(dto);

        final DefaultResponse response = restfulServiceUtil.callRestService(Url.REGISTER_VERIFY, null, authenticateRequest, null,
                DefaultResponse.class);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUpdateUserinfo(@RequestBody final UserRequest request) throws IException {
        final DefaultResponse response = restfulServiceUtil.callRestService(Url.USER_INFO, null, request, null, DefaultResponse.class);

        return createResponseEntity(response);
    }
}

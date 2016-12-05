package org.trinity.yqyl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.validator.OnValid;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.yqyl.common.message.dto.domain.TokenDto;
import org.trinity.yqyl.common.message.dto.request.TokenRequest;
import org.trinity.yqyl.common.message.dto.response.TokenResponse;
import org.trinity.yqyl.process.controller.base.ITokenProcessController;

@RestController
@RequestMapping("/security/token")
public class TokenRestController extends AbstractRestController {
    @Autowired
    private ITokenProcessController tokenProcessController;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<TokenResponse> getToken(@RequestParam("tokenName") final String tokenName) throws IException {
        final TokenResponse response = new TokenResponse();

        final TokenDto token = tokenProcessController.getToken(tokenName);

        response.addData(token);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<TokenResponse> getToken(@OnValid @RequestBody final TokenRequest request) throws IException {
        final TokenResponse response = new TokenResponse();

        final TokenDto token = tokenProcessController.refreshToken(request.getIdentity(), request.getOriginalToken());

        response.addData(token);

        return createResponseEntity(response);
    }
}

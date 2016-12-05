package org.trinity.yqyl.process.controller.base;

import org.trinity.common.accessright.AuthToken;
import org.trinity.common.exception.IException;
import org.trinity.process.controller.IProcessController;
import org.trinity.yqyl.common.message.dto.domain.TokenDto;

public interface ITokenProcessController extends IProcessController {

	TokenDto getToken(String tokenName) throws IException;

	AuthToken preAuth(String token);

	TokenDto refreshToken(String identity, String originalToken);

	void updateAccessTime(String token) throws IException;
}

package org.trinity.yqyl.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.UserDto;
import org.trinity.yqyl.common.message.dto.domain.UserSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ChangePasswordRequest;
import org.trinity.yqyl.common.message.dto.request.UserRequest;
import org.trinity.yqyl.common.message.dto.response.UserResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IUserProcessController;

@RestController
@RequestMapping("/user")
public class UserRestController
        extends AbstractApplicationAwareCrudRestController<UserDto, UserSearchingDto, IUserProcessController, UserRequest, UserResponse> {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<DefaultResponse> changePassword(@RequestBody final ChangePasswordRequest request)
            throws IException {
        getDomainProcessController().changePassword(request.getId(), request.getOldPassword(), request.getNewPassword());

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<UserResponse> getMe(final UserSearchingDto request) throws IException {
        final UserResponse response = createResponseInstance();

        final List<UserDto> data = getDomainProcessController().getMe(request);

        response.addData(data);

        return createResponseEntity(response);
    }

    @Override
    protected UserResponse createResponseInstance() {
        return new UserResponse();
    }

    @Override
    protected void validateAdd() throws IException {
        super.validateAdd();

        securityUtil.checkAccessRight(AccessRight.ADMINISTRATOR);
    }

    @Override
    protected void validateDelete() throws IException {
        super.validateDelete();
        securityUtil.checkAccessRight(AccessRight.SUPER_USER);
    }

    @Override
    protected void validateGet() throws IException {
        super.validateGet();

        securityUtil.checkAccessRight(AccessRight.ADMINISTRATOR);
    }
}

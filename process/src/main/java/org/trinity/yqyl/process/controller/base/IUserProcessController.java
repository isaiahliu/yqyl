package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.UserDto;
import org.trinity.yqyl.common.message.dto.domain.UserSearchingDto;

public interface IUserProcessController extends ICrudProcessController<UserDto, UserSearchingDto> {
    void changePassword(Long id, String oldPassword, String newPassword) throws IException;

    List<UserDto> getMe(UserSearchingDto dto) throws IException;
}

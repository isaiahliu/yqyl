package org.trinity.yqyl.process.datapermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.User;

@Component
public class UserDataPermissionValidator extends AbstractDataPermissionValidator<User> {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IExceptionFactory exceptionFactory;

    @Override
    @Authorize(AccessRight.SUPER_USER)
    public void checkSpecialPermission() throws IException {
        super.checkSpecialPermission();
    }

    @Override
    protected void validateData(final String username, final Long id) throws IException {
        final User u = userRepository.findOne(id);

        if (u != null && !u.getUsername().equals(username)) {
            throw exceptionFactory.createException(ErrorMessage.UNABLE_TO_ACCESS_USER);
        }
    }
}

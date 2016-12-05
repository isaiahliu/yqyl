package org.trinity.yqyl.process.datapermission;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.trinity.common.accessright.ITokenAwareAuthentication;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.process.datapermission.IDataPermissionValidator;

public abstract class AbstractDataPermissionValidator<T> implements IDataPermissionValidator<T> {
    @Autowired
    private IExceptionFactory exceptionFactory;

    @Override
    public <Dto> void validate(final List<Dto> data, final Function<Dto, Long> idGetter) throws IException {
        for (final Dto item : data) {
            validate(idGetter.apply(item));
        }
    }

    @Override
    public final void validate(final Long id) throws IException {
        try {
            checkSpecialPermission();
        } catch (final IException e) {
            validateData(getCurrentUser(), id);
        }
    }

    protected void checkSpecialPermission() throws IException {
    }

    protected String getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((ITokenAwareAuthentication) authentication).getToken().getUsername();
    }

    protected IExceptionFactory getExceptionFactory() {
        return exceptionFactory;
    }

    protected abstract void validateData(String username, Long id) throws IException;
}

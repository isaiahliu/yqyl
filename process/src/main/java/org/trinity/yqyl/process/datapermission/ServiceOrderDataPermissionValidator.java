package org.trinity.yqyl.process.datapermission;

import org.springframework.stereotype.Component;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;

@Component
public class ServiceOrderDataPermissionValidator extends AbstractDataPermissionValidator<ServiceOrder> {
    @Override
    public void checkSpecialPermission() throws IException {
        super.checkSpecialPermission();
    }

    @Override
    protected void validateData(final String username, final Long id) throws IException {
    }
}

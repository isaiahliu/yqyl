package org.trinity.yqyl.process.datapermission;

import org.springframework.stereotype.Component;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;

@Component
public class ServiceInfoStasticDataPermissionValidator
        extends AbstractDataPermissionValidator<ServiceInfoStastic> {
    @Override
    public void checkSpecialPermission() throws IException {
    super.checkSpecialPermission();
    }

    @Override
    protected void validateData(final String username, final Long id) throws IException {
    }
}


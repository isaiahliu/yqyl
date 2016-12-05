package org.trinity.yqyl.process.datapermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientOtherRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientOther;

@Component
public class ServiceReceiverClientOtherDataPermissionValidator extends AbstractDataPermissionValidator<ServiceReceiverClientOther> {
    @Autowired
    private IServiceReceiverClientOtherRepository serviceReceiverClientOtherRepository;

    @Override
    public void checkSpecialPermission() throws IException {
        super.checkSpecialPermission();
    }

    @Override
    protected void validateData(final String username, final Long id) throws IException {
        final ServiceReceiverClientOther entity = serviceReceiverClientOtherRepository.findOne(id);
        if (entity != null) {
            if (!entity.getServiceReceiverClient().getUser().getUsername().equals(username)) {
                getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
            }
        }
    }
}

package org.trinity.yqyl.process.datapermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientInterestRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientInterest;

@Component
public class ServiceReceiverClientInterestDataPermissionValidator extends AbstractDataPermissionValidator<ServiceReceiverClientInterest> {
    @Autowired
    private IServiceReceiverClientInterestRepository serviceReceiverClientInterestRepository;

    @Override
    public void checkSpecialPermission() throws IException {
        super.checkSpecialPermission();
    }

    @Override
    protected void validateData(final String username, final Long id) throws IException {
        final ServiceReceiverClientInterest entity = serviceReceiverClientInterestRepository.findOne(id);
        if (entity != null) {
            if (!entity.getServiceReceiverClient().getUser().getUsername().equals(username)) {
                getExceptionFactory().createException(ErrorMessage.INSUFFICIENT_ACCESSRIGHT);
            }
        }
    }
}

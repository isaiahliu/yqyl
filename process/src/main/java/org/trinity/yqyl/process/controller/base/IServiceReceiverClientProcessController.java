package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientSearchingDto;

public interface IServiceReceiverClientProcessController
        extends ICrudProcessController<ServiceReceiverClientDto, ServiceReceiverClientSearchingDto> {

    void disable(Long entityId) throws IException;

    void realnameApply(ServiceReceiverClientDto dto) throws IException;

    void realnameAudit(ServiceReceiverClientDto dto) throws IException;

    void realnameDeny(ServiceReceiverClientDto dto) throws IException;
}

package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;

public interface IServiceInfoProcessController extends ICrudProcessController<ServiceInfoDto, ServiceInfoSearchingDto> {

    String addImage(Long entityId) throws IException;

    void deleteImage(Long entityId, String uuid) throws IException;
}

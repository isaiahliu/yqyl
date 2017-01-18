package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;

public interface IServiceOrderRequirementProcessController
        extends ICrudProcessController<ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto> {

    void cancelRequirement(Long entityId) throws IException;

    void catchRequirement(Long entityId) throws IException;
}

package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRequirementRepository;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;

@Service
public class ServiceOrderRequirementProcessController extends
        AbstractAutowiredCrudProcessController<ServiceOrderRequirement, ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementRepository>
        implements IServiceOrderRequirementProcessController {

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }

}

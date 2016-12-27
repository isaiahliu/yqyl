package org.trinity.yqyl.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRequirementRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;

@Service
public class ServiceOrderRequirementProcessController extends
		AbstractAutowiredCrudProcessController<ServiceOrderRequirement, ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementRepository>
		implements IServiceOrderRequirementProcessController {

	@Autowired
	private IUserRepository userRepository;

	@Override
	protected void addRelationshipFields(final ServiceOrderRequirement entity, final ServiceOrderRequirementDto dto) throws IException {
		entity.setUser(userRepository.findOneByUsername(getCurrentUsername()));
	}

	@Override
	protected boolean canAccessAllStatus() {
		return true;
	}

}

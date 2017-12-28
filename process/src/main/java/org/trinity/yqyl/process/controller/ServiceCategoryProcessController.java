package org.trinity.yqyl.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategorySearchingDto;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IContentProcessController;
import org.trinity.yqyl.process.controller.base.IServiceCategoryProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceCategoryRepository;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;

@Service
public class ServiceCategoryProcessController extends
        AbstractAutowiredCrudProcessController<ServiceCategory, ServiceCategoryDto, ServiceCategorySearchingDto, IServiceCategoryRepository>
        implements IServiceCategoryProcessController {

    @Autowired
    private IContentProcessController contentProcessController;

    @Override
    protected void addRelationshipFields(final ServiceCategory entity, final ServiceCategoryDto dto) throws IException {
        if (dto.getParent() != null && dto.getParent().getId() != null && dto.getParent().getId() != 0) {
            entity.setParent(getDomainEntityRepository().findOne(dto.getParent().getId()));
        }

        if (dto.getParent() == null || dto.getParent().getId() == null || dto.getParent().getId().equals(0L)) {
            entity.setImage(contentProcessController.create());
        }
    }

    @Override
    protected boolean canAccessAllStatus() {
        return getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
    }
}

package org.trinity.yqyl.process.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.IPagingDto;
import org.trinity.common.dto.object.ISearchingDto;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.process.controller.AbstractCrudProcessController;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.process.datapermission.IDataPermissionValidator;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.lookup.AccessRight;

public abstract class AbstractAutowiredCrudProcessController<TEntity, TDto extends AbstractBusinessDto, TSearchingDto extends ISearchingDto, TRepository extends IJpaRepository<TEntity, TSearchingDto>>
        extends AbstractCrudProcessController<TEntity, TDto, TSearchingDto> {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @Autowired
    private IObjectConverter<IPagingDto, Pageable> pagingConverter;

    @Autowired
    private TRepository domainEntityRepository;

    @Autowired
    private IObjectConverter<TEntity, TDto> domainObjectConverter;

    @Autowired
    private IDataPermissionValidator<TEntity> domainDataPermissionValidator;

    @Autowired
    private IExceptionFactory exceptionFactory;

    @Override
    public IDataPermissionValidator<TEntity> getDomainDataPermissionValidator() {
        return domainDataPermissionValidator;
    }

    public ISecurityUtil<AccessRight> getSecurityUtil() {
        return securityUtil;
    }

    public void setPagingConverter(final IObjectConverter<IPagingDto, Pageable> pagingConverter) {
        this.pagingConverter = pagingConverter;
    }

    @Override
    protected boolean canAccessAllStatus() {
        return getSecurityUtil().hasAccessRight(AccessRight.SUPER_USER);
    }

    @Override
    protected boolean canAccessScopeAll() {
        return getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
    }

    @Override
    protected Pageable createPageable(final TSearchingDto data) {
        return getPagingConverter().convert(data);
    }

    @Override
    protected String getCurrentUsername() {
        try {
            return getSecurityUtil().getCurrentToken().getUsername();
        } catch (final IException e) {
            return null;
        }
    }

    @Override
    protected TRepository getDomainEntityRepository() {
        return domainEntityRepository;
    }

    @Override
    protected IObjectConverter<TEntity, TDto> getDomainObjectConverter() {
        return domainObjectConverter;
    }

    @Override
    protected IExceptionFactory getExceptionFactory() {
        return exceptionFactory;
    }

    protected IObjectConverter<IPagingDto, Pageable> getPagingConverter() {
        return pagingConverter;
    }

    @Override
    protected void prepareSearch(final TSearchingDto data) {
        super.prepareSearch(data);
    }

    protected void setSecurityUtil(final ISecurityUtil<AccessRight> securityUtil) {
        this.securityUtil = securityUtil;
    }
}

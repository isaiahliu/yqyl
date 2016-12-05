package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.yqyl.common.message.dto.domain.UserDto;
import org.trinity.yqyl.common.message.dto.domain.UserSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.UserStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IUserProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.User_;

@Service
public class UserProcessController extends AbstractAutowiredCrudProcessController<User, UserDto, UserSearchingDto, IUserRepository>
        implements IUserProcessController {
    @Override
    public void changePassword(final Long id, final String oldPassword, final String newPassword) throws IException {
        final User user = getDomainEntityRepository().findOne(id);

        validateDataPermission(getDomainObjectConverter().convert(user));

        if (!user.getPassword().equals(oldPassword)) {
            throw getExceptionFactory().createException(ErrorMessage.WRONG_PASSWORD);
        }

        user.setPassword(newPassword);

        getDomainEntityRepository().save(user);
    }

    @Override
    public List<UserDto> getMe(final UserSearchingDto dto) throws IException {
        final String username = getSecurityUtil().getCurrentToken().getUsername();

        final User user = getDomainEntityRepository().findOneByUsername(username);
        final UserDto userDto = getDomainObjectConverter().convert(user, dto.generateRelationship());

        final List<UserDto> result = new ArrayList<>();
        result.add(userDto);
        return result;
    }

    @Override
    protected void addRelationshipFields(final User entity, final UserDto dto) {
        entity.setStatus(UserStatus.ACTIVE);
    }

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }

    @Override
    protected void prepareSearch(final UserSearchingDto data) {
        super.prepareSearch(data);

        final Specification<User> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.join(User_.accessrights), AccessRight.SUPER_USER));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        data.setExceptUserIds(
                getDomainEntityRepository().findAll(specification).stream().map(item -> item.getId()).collect(Collectors.toList()));
    }

    @Override
    protected void updateRelationshipFields(final User entity, final UserDto dto) throws IException {
        if (dto.getAccessrights() != null) {
            if (getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR)) {
                entity.getAccessrights().clear();
                if (!dto.getAccessrights().isEmpty()) {
                    dto.getAccessrights()
                            .forEach(item -> entity.getAccessrights().add(LookupParser.parse(AccessRight.class, item.getCode())));
                }
            }
        }
    }

    @Override
    protected void validateDataPermission(final UserDto dto) throws IException {
        final String currentUser = getSecurityUtil().getCurrentToken().getUsername();

        final User user = getDomainEntityRepository().findOne(dto.getId());

        if (!getSecurityUtil().hasAccessRight(AccessRight.SUPER_USER)) {
            if (!currentUser.equals(user.getUsername())) {
                throw getExceptionFactory().createException(ErrorMessage.UNABLE_TO_ACCESS_USER, user.getUsername());
            }
        }
    }
}

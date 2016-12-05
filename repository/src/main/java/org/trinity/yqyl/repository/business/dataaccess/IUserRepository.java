package org.trinity.yqyl.repository.business.dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.UserSearchingDto;
import org.trinity.yqyl.common.message.lookup.UserStatus;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IUserRepository extends IJpaRepository<User, UserSearchingDto> {
    User findOneByCellphone(String cellphone);

    User findOneByUsername(String username);

    @Override
    default Page<User> query(final UserSearchingDto searchingDto, final Pageable pagable) {
        final Specification<User> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(searchingDto.getUsername())) {
                predicates.add(cb.like(root.get(User_.username), "%" + searchingDto.getUsername() + "%"));
            }

            if (searchingDto.getId() != null && searchingDto.getId() > 0) {
                predicates.add(cb.equal(root.get(User_.id), searchingDto.getId()));
            }

            if (!searchingDto.getExceptUserIds().isEmpty()) {
                predicates.add(root.get(User_.id).in(searchingDto.getExceptUserIds()).not());
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(User_.status), UserStatus.ACTIVE));
                }
            } else {
                final In<UserStatus> in = cb.in(root.get(User_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(UserStatus.class, item)).forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification, pagable);
    }
}

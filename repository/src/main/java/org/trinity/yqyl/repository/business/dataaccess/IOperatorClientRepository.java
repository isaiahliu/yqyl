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
import org.trinity.yqyl.common.message.dto.domain.OperatorClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;
import org.trinity.yqyl.repository.business.entity.OperatorClient;
import org.trinity.yqyl.repository.business.entity.OperatorClient_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IOperatorClientRepository extends IJpaRepository<OperatorClient, OperatorClientSearchingDto> {
    @Override
    default Page<OperatorClient> query(final OperatorClientSearchingDto searchingDto, final Pageable pagable) {
        final Specification<OperatorClient> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(OperatorClient_.userId), searchingDto.getId()));
            }

            if (!StringUtils.isEmpty(searchingDto.getUsername())) {
                predicates.add(cb.like(root.join(OperatorClient_.user).get(User_.username), "%" + searchingDto.getUsername() + "%"));
            }

            if (!StringUtils.isEmpty(searchingDto.getName())) {
                predicates.add(cb.like(root.get(OperatorClient_.name), "%" + searchingDto.getName() + "%"));
            }

            if (!StringUtils.isEmpty(searchingDto.getStaffNo())) {
                predicates.add(cb.like(root.get(OperatorClient_.staffNo), "%" + searchingDto.getStaffNo() + "%"));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(OperatorClient_.status), OperatorClientStatus.ACTIVE));
                }
            } else {
                final In<OperatorClientStatus> in = cb.in(root.get(OperatorClient_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(OperatorClientStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification, pagable);
    }
}

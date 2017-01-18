package org.trinity.yqyl.repository.business.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceOrderRequirementRepository extends IJpaRepository<ServiceOrderRequirement, ServiceOrderRequirementSearchingDto> {
    @Override
    default Page<ServiceOrderRequirement> query(final ServiceOrderRequirementSearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceOrderRequirement> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(root.get(ServiceOrderRequirement_.user).get(User_.username), searchingDto.getCurrentUsername()));
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(ServiceOrderRequirement_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(ServiceOrderRequirement_.status), ServiceOrderRequirementStatus.ACTIVE));
                }
            } else {
                final In<ServiceOrderRequirementStatus> in = cb.in(root.get(ServiceOrderRequirement_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(ServiceOrderRequirementStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            if (searchingDto.getAfter() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(ServiceOrderRequirement_.announceTime), new Date(searchingDto.getAfter())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

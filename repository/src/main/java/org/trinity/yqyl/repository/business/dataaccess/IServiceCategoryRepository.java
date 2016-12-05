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
import org.trinity.yqyl.common.message.dto.domain.ServiceCategorySearchingDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceCategory_;

public interface IServiceCategoryRepository extends IJpaRepository<ServiceCategory, ServiceCategorySearchingDto> {
    List<ServiceCategory> findAllByParent(ServiceCategory parent);

    @Override
    default Page<ServiceCategory> query(final ServiceCategorySearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceCategory> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(searchingDto.getName())) {
                predicates.add(cb.like(root.get(ServiceCategory_.name), "%" + searchingDto.getName() + "%"));
            }

            if (searchingDto.getId() != null && searchingDto.getId() > 0) {
                predicates.add(cb.equal(root.get(ServiceCategory_.id), searchingDto.getId()));
            } else if (searchingDto.getParentId() == null || searchingDto.getParentId() == 0) {
                predicates.add(cb.isNull(root.get(ServiceCategory_.parent)));
            } else {
                predicates.add(cb.equal(root.join(ServiceCategory_.parent).get(ServiceCategory_.id), searchingDto.getParentId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(ServiceCategory_.status), RecordStatus.ACTIVE));
                }
            } else {
                final In<RecordStatus> in = cb.in(root.get(ServiceCategory_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(RecordStatus.class, item)).forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification, pagable);
    }
}

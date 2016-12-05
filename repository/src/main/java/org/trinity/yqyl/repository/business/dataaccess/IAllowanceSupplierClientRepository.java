package org.trinity.yqyl.repository.business.dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;
import org.trinity.yqyl.repository.business.entity.AllowanceSupplierClient;
import org.trinity.yqyl.repository.business.entity.AllowanceSupplierClient_;

public interface IAllowanceSupplierClientRepository extends IJpaRepository<AllowanceSupplierClient, AllowanceSupplierClientSearchingDto> {
    @Override
    default Page<AllowanceSupplierClient> query(final AllowanceSupplierClientSearchingDto searchingDto, final Pageable pagable) {
        final Specification<AllowanceSupplierClient> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(AllowanceSupplierClient_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(AllowanceSupplierClient_.status), OperatorClientStatus.ACTIVE));
                }
            } else {
                final In<OperatorClientStatus> in = cb.in(root.get(AllowanceSupplierClient_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(OperatorClientStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

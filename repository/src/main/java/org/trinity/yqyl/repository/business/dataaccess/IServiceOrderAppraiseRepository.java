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
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseSearchingDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceInfo_;
import org.trinity.yqyl.repository.business.entity.ServiceOrderAppraise;
import org.trinity.yqyl.repository.business.entity.ServiceOrderAppraise_;
import org.trinity.yqyl.repository.business.entity.ServiceOrder_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;

public interface IServiceOrderAppraiseRepository extends IJpaRepository<ServiceOrderAppraise, ServiceOrderAppraiseSearchingDto> {
    @Override
    default Page<ServiceOrderAppraise> query(final ServiceOrderAppraiseSearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceOrderAppraise> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(ServiceOrderAppraise_.serviceOrderId), searchingDto.getId()));
            }

            if (searchingDto.getServiceSupplierClientId() != null && searchingDto.getServiceSupplierClientId() > 0) {
                predicates.add(cb.equal(
                        root.join(ServiceOrderAppraise_.serviceOrder).join(ServiceOrder_.serviceInfo)
                                .join(ServiceInfo_.serviceSupplierClient).get(ServiceSupplierClient_.userId),
                        searchingDto.getServiceSupplierClientId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(ServiceOrderAppraise_.status), RecordStatus.ACTIVE));
                }
            } else {
                final In<RecordStatus> in = cb.in(root.get(ServiceOrderAppraise_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(RecordStatus.class, item)).forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

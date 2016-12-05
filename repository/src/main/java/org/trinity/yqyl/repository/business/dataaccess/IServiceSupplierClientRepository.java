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
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory_;
import org.trinity.yqyl.repository.business.entity.ServiceInfo_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceSupplierClientRepository extends IJpaRepository<ServiceSupplierClient, ServiceSupplierClientSearchingDto> {
    @Override
    default Page<ServiceSupplierClient> query(final ServiceSupplierClientSearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceSupplierClient> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (!searchingDto.getCategoryChildren().isEmpty()) {
                predicates.add(root.join(ServiceSupplierClient_.serviceInfos).join(ServiceInfo_.serviceCategory).get(ServiceCategory_.id)
                        .in(searchingDto.getCategoryChildren()));
                query.distinct(true);
            }

            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(root.join(ServiceSupplierClient_.user).get(User_.username), searchingDto.getCurrentUsername()));
            }

            if (!StringUtils.isEmpty(searchingDto.getName())) {
                predicates.add(cb.like(root.get(ServiceSupplierClient_.name), "%" + searchingDto.getName() + "%"));
            }

            if (!StringUtils.isEmpty(searchingDto.getAddress())) {
                predicates.add(cb.like(root.get(ServiceSupplierClient_.address), "%" + searchingDto.getAddress() + "%"));
            }

            if (searchingDto.getId() != null && searchingDto.getId() != 0) {
                predicates.add(cb.equal(root.get(ServiceSupplierClient_.userId), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(ServiceSupplierClient_.status), ServiceSupplierClientStatus.ACTIVE));
                }
            } else {
                final In<ServiceSupplierClientStatus> in = cb.in(root.get(ServiceSupplierClient_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(ServiceSupplierClientStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification, pagable);
    }
}

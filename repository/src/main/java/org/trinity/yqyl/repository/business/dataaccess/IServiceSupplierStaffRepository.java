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
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffSearchingDto;
import org.trinity.yqyl.common.message.lookup.StaffStatus;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceSupplierStaffRepository extends IJpaRepository<ServiceSupplierStaff, ServiceSupplierStaffSearchingDto> {
    @Override
    default Page<ServiceSupplierStaff> query(final ServiceSupplierStaffSearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceSupplierStaff> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(
                        root.join(ServiceSupplierStaff_.serviceSupplierClient).join(ServiceSupplierClient_.user).get(User_.username),
                        searchingDto.getCurrentUsername()));
            }

            if (!StringUtils.isEmpty(searchingDto.getName())) {
                predicates.add(cb.like(root.get(ServiceSupplierStaff_.name), "%" + searchingDto.getName() + "%"));
            }

            if (!StringUtils.isEmpty(searchingDto.getCode())) {
                predicates.add(cb.like(root.get(ServiceSupplierStaff_.code), "%" + searchingDto.getCode() + "%"));
            }

            if (!searchingDto.getStatus().isEmpty()) {
                final In<StaffStatus> in = cb.in(root.get(ServiceSupplierStaff_.status));
                searchingDto.getStatus().forEach(item -> in.value(LookupParser.parse(StaffStatus.class, item)));
                predicates.add(in);
            }

            if (searchingDto.getId() != null && searchingDto.getId() != 0) {
                predicates.add(cb.equal(root.get(ServiceSupplierStaff_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(ServiceSupplierStaff_.status), StaffStatus.ACTIVE));
                }
            } else {
                final In<StaffStatus> in = cb.in(root.get(ServiceSupplierStaff_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(StaffStatus.class, item)).forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

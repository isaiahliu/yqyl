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
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceReceiverClientRepository extends IJpaRepository<ServiceReceiverClient, ServiceReceiverClientSearchingDto> {
    @Override
    default Page<ServiceReceiverClient> query(final ServiceReceiverClientSearchingDto searchingDto, final Pageable pagable) {
        final Specification<ServiceReceiverClient> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(root.join(ServiceReceiverClient_.user).get(User_.username), searchingDto.getCurrentUsername()));
            }

            if (!StringUtils.isEmpty(searchingDto.getId())) {
                predicates.add(cb.equal(root.get(ServiceReceiverClient_.id), searchingDto.getId()));
            }

            if (!StringUtils.isEmpty(searchingDto.getUserId())) {
                predicates.add(cb.equal(root.join(ServiceReceiverClient_.user).get(User_.id), searchingDto.getUserId()));
            }

            if (!StringUtils.isEmpty(searchingDto.getName())) {
                predicates.add(cb.like(root.get(ServiceReceiverClient_.name), "%" + searchingDto.getName() + "%"));
            }

            if (!StringUtils.isEmpty(searchingDto.getIdentity())) {
                predicates.add(cb.like(root.get(ServiceReceiverClient_.identityCard), "%" + searchingDto.getIdentity() + "%"));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.notEqual(root.get(ServiceReceiverClient_.status), ServiceReceiverClientStatus.DISABLED));
                }
            } else {
                final In<ServiceReceiverClientStatus> in = cb.in(root.get(ServiceReceiverClient_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(ServiceReceiverClientStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return findAll(specification, pagable);
    }
}

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
import org.trinity.yqyl.common.message.dto.domain.AccountSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccountStatus;
import org.trinity.yqyl.repository.business.entity.Account;
import org.trinity.yqyl.repository.business.entity.Account_;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient_;
import org.trinity.yqyl.repository.business.entity.User_;
import org.trinity.yqyl.repository.business.entity.Yiquan_;

public interface IAccountRepository extends IJpaRepository<Account, AccountSearchingDto> {
    @Override
    default Page<Account> query(final AccountSearchingDto searchingDto, final Pageable pagable) {
        final Specification<Account> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(root.join(Account_.yiquans).join(Yiquan_.serviceReceiverClients).join(ServiceReceiverClient_.user)
                        .get(User_.username), searchingDto.getCurrentUsername()));
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(Account_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(Account_.status), AccountStatus.ACTIVE));
                }
            } else {
                final In<AccountStatus> in = cb.in(root.get(Account_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(AccountStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

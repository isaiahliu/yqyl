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
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccountBalanceStatus;
import org.trinity.yqyl.repository.business.entity.AccountBalance;
import org.trinity.yqyl.repository.business.entity.AccountBalance_;
import org.trinity.yqyl.repository.business.entity.Account_;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient_;
import org.trinity.yqyl.repository.business.entity.User_;
import org.trinity.yqyl.repository.business.entity.Yiquan_;

public interface IAccountBalanceRepository extends IJpaRepository<AccountBalance, AccountBalanceSearchingDto> {
    @Override
    default Page<AccountBalance> query(final AccountBalanceSearchingDto searchingDto, final Pageable pagable) {
        final Specification<AccountBalance> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(root.join(AccountBalance_.account).join(Account_.yiquans).join(Yiquan_.serviceReceiverClients)
                        .join(ServiceReceiverClient_.user).get(User_.username), searchingDto.getCurrentUsername()));
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(AccountBalance_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(AccountBalance_.status), AccountBalanceStatus.ACTIVE));
                }
            } else {
                final In<AccountBalanceStatus> in = cb.in(root.get(AccountBalance_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(AccountBalanceStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

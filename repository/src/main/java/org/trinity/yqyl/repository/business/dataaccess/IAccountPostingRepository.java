package org.trinity.yqyl.repository.business.dataaccess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.AccountPostingStatus;
import org.trinity.yqyl.repository.business.entity.AccountBalance_;
import org.trinity.yqyl.repository.business.entity.AccountPosting;
import org.trinity.yqyl.repository.business.entity.AccountPosting_;
import org.trinity.yqyl.repository.business.entity.AccountTransaction_;
import org.trinity.yqyl.repository.business.entity.Account_;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient_;
import org.trinity.yqyl.repository.business.entity.User_;
import org.trinity.yqyl.repository.business.entity.Yiquan_;

public interface IAccountPostingRepository extends IJpaRepository<AccountPosting, AccountPostingSearchingDto> {
    @Override
    default Page<AccountPosting> query(final AccountPostingSearchingDto searchingDto, final Pageable pagable) {
        final Specification<AccountPosting> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
                predicates.add(cb.equal(
                        root.join(AccountPosting_.accountBalance).join(AccountBalance_.account).join(Account_.yiquans)
                                .join(Yiquan_.serviceReceiverClients).join(ServiceReceiverClient_.user).get(User_.username),
                        searchingDto.getCurrentUsername()));
                query.distinct(true);
            }

            if (!StringUtils.isEmpty(searchingDto.getCategory())) {
                predicates.add(cb.equal(root.join(AccountPosting_.accountBalance).get(AccountBalance_.category),
                        LookupParser.parse(AccountCategory.class, searchingDto.getCategory())));
            }

            if (!StringUtils.isEmpty(searchingDto.getFromDate())) {
                final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                try {
                    final Date fromDate = dateFormat.parse(searchingDto.getFromDate());
                    predicates.add(cb.greaterThanOrEqualTo(root.join(AccountPosting_.accountTransaction).get(AccountTransaction_.timestamp),
                            fromDate));
                } catch (final ParseException e) {
                }
            }

            if (!StringUtils.isEmpty(searchingDto.getToDate())) {
                final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                try {
                    final Date toDate = dateFormat.parse(searchingDto.getToDate());
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(toDate);
                    calendar.add(Calendar.DATE, 1);

                    predicates.add(cb.lessThan(root.join(AccountPosting_.accountTransaction).get(AccountTransaction_.timestamp),
                            calendar.getTime()));
                } catch (final ParseException e) {
                }
            }

            if (searchingDto.getYiquanId() != null) {
                predicates.add(cb.equal(
                        root.join(AccountPosting_.accountBalance).join(AccountBalance_.account).join(Account_.yiquans).get(Yiquan_.id),
                        searchingDto.getYiquanId()));
                query.distinct(true);
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(AccountPosting_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(AccountPosting_.status), AccountPostingStatus.ACTIVE));
                }
            } else {
                final In<AccountPostingStatus> in = cb.in(root.get(AccountPosting_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(AccountPostingStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

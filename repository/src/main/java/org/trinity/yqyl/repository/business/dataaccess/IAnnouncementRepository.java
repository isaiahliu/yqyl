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
import org.trinity.yqyl.common.message.dto.domain.AnnouncementSearchingDto;
import org.trinity.yqyl.common.message.lookup.AnnouncementStatus;
import org.trinity.yqyl.repository.business.entity.Announcement;
import org.trinity.yqyl.repository.business.entity.Announcement_;

public interface IAnnouncementRepository extends IJpaRepository<Announcement, AnnouncementSearchingDto> {
    @Override
    default Page<Announcement> query(final AnnouncementSearchingDto searchingDto, final Pageable pagable) {
        final Specification<Announcement> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (!searchingDto.isSearchAll()) {
            }

            if (searchingDto.getId() != null) {
                predicates.add(cb.equal(root.get(Announcement_.id), searchingDto.getId()));
            }

            if (searchingDto.getStatus().isEmpty()) {
                if (!searchingDto.isSearchAllStatus()) {
                    predicates.add(cb.equal(root.get(Announcement_.status), AnnouncementStatus.ACTIVE));
                }
            } else {
                final In<AnnouncementStatus> in = cb.in(root.get(Announcement_.status));
                searchingDto.getStatus().stream().map(item -> LookupParser.parse(AnnouncementStatus.class, item))
                        .forEach(item -> in.value(item));
                predicates.add(in);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return findAll(specification, pagable);
    }
}

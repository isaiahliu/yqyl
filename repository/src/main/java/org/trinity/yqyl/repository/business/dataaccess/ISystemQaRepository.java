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
import org.trinity.yqyl.common.message.dto.domain.SystemQaSearchingDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.SystemQa;
import org.trinity.yqyl.repository.business.entity.SystemQa_;

public interface ISystemQaRepository extends IJpaRepository<SystemQa, SystemQaSearchingDto> {
	@Override
	default Page<SystemQa> query(final SystemQaSearchingDto searchingDto, final Pageable pagable) {
		final Specification<SystemQa> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			if (!searchingDto.isSearchAll()) {
			}

			if (searchingDto.getId() != null) {
				predicates.add(cb.equal(root.get(SystemQa_.id), searchingDto.getId()));
			}

			if (!StringUtils.isEmpty(searchingDto.getQuestion())) {
				predicates.add(cb.like(root.get(SystemQa_.question), "%" + searchingDto.getQuestion() + "%"));
			}

			if (searchingDto.getStatus().isEmpty()) {
				if (!searchingDto.isSearchAllStatus()) {
					predicates.add(cb.equal(root.get(SystemQa_.status), RecordStatus.ACTIVE));
				}
			} else {
				final In<RecordStatus> in = cb.in(root.get(SystemQa_.status));
				searchingDto.getStatus().stream().map(item -> LookupParser.parse(RecordStatus.class, item))
						.forEach(item -> in.value(item));
				predicates.add(in);
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return findAll(specification, pagable);
	}
}

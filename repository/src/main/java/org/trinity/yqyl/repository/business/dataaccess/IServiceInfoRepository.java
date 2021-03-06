package org.trinity.yqyl.repository.business.dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.lookup.City;
import org.trinity.yqyl.common.message.lookup.Province;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory_;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic_;
import org.trinity.yqyl.repository.business.entity.ServiceInfo_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceInfoRepository extends IJpaRepository<ServiceInfo, ServiceInfoSearchingDto> {
	@Override
	default Page<ServiceInfo> query(final ServiceInfoSearchingDto searchingDto, final Pageable pagable) {
		final Specification<ServiceInfo> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();

			if (searchingDto.getId() != null) {
				predicates.add(cb.equal(root.get(ServiceInfo_.id), searchingDto.getId()));
			}

			if (!searchingDto.isSearchAll()) {
				predicates.add(cb.equal(root.join(ServiceInfo_.serviceSupplierClient).join(ServiceSupplierClient_.user).get(User_.username),
						searchingDto.getCurrentUsername()));
			}

			if (searchingDto.getServiceSupplierClientId() != null) {
				predicates.add(cb.equal(root.join(ServiceInfo_.serviceSupplierClient).get(ServiceSupplierClient_.userId),
						searchingDto.getServiceSupplierClientId()));
			}

			if (!StringUtils.isEmpty(searchingDto.getName())) {
				predicates.add(cb.or(cb.like(root.get(ServiceInfo_.name), "%" + searchingDto.getName() + "%"),
						cb.like(root.join(ServiceInfo_.serviceSupplierClient).get(ServiceSupplierClient_.name),
								"%" + searchingDto.getName() + "%")));
			}

			if (searchingDto.getCategoryId() != null && searchingDto.getCategoryId() > 0) {
				predicates.add(cb.equal(root.join(ServiceInfo_.serviceCategory).get(ServiceCategory_.id), searchingDto.getCategoryId()));
			}

			if (searchingDto.getParentCategoryId() != null && searchingDto.getParentCategoryId() > 0) {
				predicates.add(cb.equal(root.join(ServiceInfo_.serviceCategory).join(ServiceCategory_.parent).get(ServiceCategory_.id),
						searchingDto.getParentCategoryId()));
			}

			if (!searchingDto.getStatus().isEmpty()) {
				final In<ServiceStatus> in = cb.in(root.get(ServiceInfo_.status));
				searchingDto.getStatus().forEach(item -> in.value(LookupParser.parse(ServiceStatus.class, item)));
				predicates.add(in);
			} else if (!searchingDto.isSearchAllStatus()) {
				predicates.add(cb.equal(root.get(ServiceInfo_.status), ServiceStatus.ACTIVE));
			}

			if (!StringUtils.isEmpty(searchingDto.getProvince())) {
				final Province province = LookupParser.parse(Province.class, searchingDto.getProvince());
				if (province != null) {
					predicates.add(cb.or(cb.equal(root.get(ServiceInfo_.province), province), cb.isNull(root.get(ServiceInfo_.province))));
				}
			}

			if (!StringUtils.isEmpty(searchingDto.getCity())) {
				final City city = LookupParser.parse(City.class, searchingDto.getCity());
				if (city != null) {
					predicates.add(cb.or(cb.equal(root.get(ServiceInfo_.city), city), cb.isNull(root.get(ServiceInfo_.city))));
				}
			}

			switch (searchingDto.getCustomSortedBy()) {
				case "sales":
					query.distinct(true);
					if ("desc".equals(searchingDto.getCustomSortedDirection())) {
						query.orderBy(
								cb.desc(root.join(ServiceInfo_.serviceInfoStastic, JoinType.LEFT).get(ServiceInfoStastic_.orderCount)));
					} else {
						query.orderBy(
								cb.asc(root.join(ServiceInfo_.serviceInfoStastic, JoinType.LEFT).get(ServiceInfoStastic_.orderCount)));
					}
					break;
				case "appraise":
					query.distinct(true);
					if ("desc".equals(searchingDto.getCustomSortedDirection())) {
						query.orderBy(
								cb.desc(root.join(ServiceInfo_.serviceInfoStastic, JoinType.LEFT).get(ServiceInfoStastic_.appraiseAvg)));
					} else {
						query.orderBy(
								cb.asc(root.join(ServiceInfo_.serviceInfoStastic, JoinType.LEFT).get(ServiceInfoStastic_.appraiseAvg)));
					}
					break;
				default:
					break;
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return findAll(specification, pagable);
	}
}

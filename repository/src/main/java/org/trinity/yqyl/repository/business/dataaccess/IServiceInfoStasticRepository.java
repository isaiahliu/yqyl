package org.trinity.yqyl.repository.business.dataaccess;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticSearchingDto;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic_;

public interface IServiceInfoStasticRepository extends IJpaRepository<ServiceInfoStastic, ServiceInfoStasticSearchingDto> {
	ServiceInfoStastic findOneByServiceInfo(ServiceInfo serviceInfo);

	@Override
	default Page<ServiceInfoStastic> query(final ServiceInfoStasticSearchingDto searchingDto, final Pageable pagable) {
		final Specification<ServiceInfoStastic> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			if (!searchingDto.isSearchAll()) {
			}

			if (searchingDto.getId() != null) {
				predicates.add(cb.equal(root.get(ServiceInfoStastic_.serviceInfoId), searchingDto.getId()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
		return findAll(specification, pagable);
	}

	@Modifying
	@Query("update ServiceInfoStastic set appraiseAvg=(appraiseAvg * appraiseCount + :rate) / (appraiseCount + 1), appraiseCount = appraiseCount + 1 where serviceInfoId=:serviceInfoId")
	int updateForNewAppraise(@Param("rate") Double rate, @Param("serviceInfoId") Long serviceInfoId);

	@Modifying
	@Query("update ServiceInfoStastic set orderCount=orderCount+1 where serviceInfoId=:serviceInfoId")
	int updateForNewOrder(@Param("serviceInfoId") Long serviceInfoId);
}

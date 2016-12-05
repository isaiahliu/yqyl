package org.trinity.yqyl.repository.business.dataaccess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.trinity.common.dto.object.ISearchingDto;
import org.trinity.message.LookupParser;
import org.trinity.repository.repository.IJpaRepository;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.repository.business.entity.ServiceCategory_;
import org.trinity.yqyl.repository.business.entity.ServiceInfo_;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrder_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.User_;

public interface IServiceOrderRepository extends IJpaRepository<ServiceOrder, ServiceOrderSearchingDto> {
	@Query("select count(0) from ServiceOrder where user=:user and status in (:status)")
	int countUnprocessedOrders(@Param("user") User user, @Param("status") OrderStatus[] status);

	@Override
	default Page<ServiceOrder> query(final ServiceOrderSearchingDto searchingDto, final Pageable pagable) {
		final Specification<ServiceOrder> specification = (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			switch (searchingDto.getSearchScope()) {
				case ISearchingDto.SEARCH_ALL:
					break;
				case "SUPPLIER":
					predicates.add(cb.equal(root.join(ServiceOrder_.serviceInfo).join(ServiceInfo_.serviceSupplierClient)
							.join(ServiceSupplierClient_.user).get(User_.username), searchingDto.getCurrentUsername()));
					break;
				case ISearchingDto.SEARCH_ME:
				default:
					predicates.add(cb.equal(root.join(ServiceOrder_.user).get(User_.username), searchingDto.getCurrentUsername()));
					break;
			}

			if (!StringUtils.isEmpty(searchingDto.getReceiverUserName())) {
				predicates.add(cb.like(root.join(ServiceOrder_.user).get(User_.username), "%" + searchingDto.getReceiverUserName() + "%"));
			}

			if (searchingDto.getId() != null && searchingDto.getId() > 0) {
				predicates.add(cb.equal(root.get(ServiceOrder_.id), searchingDto.getId()));
			}

			if (!StringUtils.isEmpty(searchingDto.getCategory())) {
				predicates.add(cb.equal(root.join(ServiceOrder_.serviceInfo).join(ServiceInfo_.serviceCategory).get(ServiceCategory_.name),
						searchingDto.getCategory()));
			}

			if (!searchingDto.getStatus().isEmpty()) {
				final In<OrderStatus> in = cb.in(root.get(ServiceOrder_.status));
				searchingDto.getStatus().forEach(item -> in.value(LookupParser.parse(OrderStatus.class, item)));
				predicates.add(in);
			}

			if (searchingDto.getServiceSupplierClientId() != null) {
				predicates.add(cb.equal(
						root.join(ServiceOrder_.serviceInfo).join(ServiceInfo_.serviceSupplierClient).get(ServiceSupplierClient_.userId),
						searchingDto.getServiceSupplierClientId()));

				query.distinct(true);
			}

			if (!StringUtils.isEmpty(searchingDto.getSupplierUserName())) {
				predicates.add(cb.like(root.join(ServiceOrder_.serviceInfo).join(ServiceInfo_.serviceSupplierClient)
						.join(ServiceSupplierClient_.user).get(User_.username), "%" + searchingDto.getSupplierUserName() + "%"));
			}

			if (!StringUtils.isEmpty(searchingDto.getServiceDate())) {
				final DateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {
					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(format.parse(searchingDto.getServiceDate()));
					predicates.add(cb.greaterThanOrEqualTo(root.get(ServiceOrder_.serviceTime), calendar.getTime()));

					calendar.add(Calendar.DATE, 1);
					predicates.add(cb.lessThan(root.get(ServiceOrder_.serviceTime), calendar.getTime()));
				} catch (final ParseException e) {
				}
			}

			if (!StringUtils.isEmpty(searchingDto.getSettledDate())) {
				final DateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {
					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(format.parse(searchingDto.getSettledDate()));
					predicates.add(cb.greaterThanOrEqualTo(root.get(ServiceOrder_.settledTime), calendar.getTime()));

					calendar.add(Calendar.DATE, 1);
					predicates.add(cb.lessThan(root.get(ServiceOrder_.settledTime), calendar.getTime()));
				} catch (final ParseException e) {
				}
			}

			if (!StringUtils.isEmpty(searchingDto.getPaymentMethod())) {
				predicates.add(cb.equal(root.get(ServiceOrder_.paymentMethod),
						LookupParser.parse(PaymentMethod.class, searchingDto.getPaymentMethod())));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return findAll(specification, pagable);
	}
}

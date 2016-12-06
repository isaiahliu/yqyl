package org.trinity.yqyl.process.converter;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.lookup.StaffStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff;

@Component
public class ServiceSupplierStaffConverter extends AbstractLookupSupportObjectConverter<ServiceSupplierStaff, ServiceSupplierStaffDto> {
	private static enum ServiceSupplierStaffRelationship {
		SERVICE_SUPPLIER_CLIENT, SERVICE_CATEGORIES, SERVICE_ORDERS, NA
	}

	@Autowired
	private IObjectConverter<ServiceCategory, ServiceCategoryDto> serviceCategoryConverter;

	@Autowired
	private IObjectConverter<ServiceOrder, ServiceOrderDto> serviceOrderConverter;

	@Autowired
	private IObjectConverter<ServiceSupplierClient, ServiceSupplierClientDto> serviceSupplierClientConverter;

	@Autowired
	public ServiceSupplierStaffConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceSupplierStaffDto source, final ServiceSupplierStaff target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
		copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
		copyObject(source::getDob, target::getDob, target::setDob, copyPolicy);
		copyObject(source::getIdentityCard, target::getIdentityCard, target::setIdentityCard, copyPolicy);
		copyObject(source::getName, target::getName, target::setName, copyPolicy);
		copyObject(source::getPhoneNo, target::getPhoneNo, target::setPhoneNo, copyPolicy);
		copyObject(source::getPhoto, target::getPhoto, target::setPhoto, copyPolicy);
		copyLookup(source::getStatus, target::getStatus, target::setStatus, StaffStatus.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceSupplierStaff source, final ServiceSupplierStaffDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
		copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
		copyObject(source::getDob, target::getDob, target::setDob, copyPolicy);
		copyObject(source::getIdentityCard, target::getIdentityCard, target::setIdentityCard, copyPolicy);
		copyObject(source::getName, target::getName, target::setName, copyPolicy);
		copyObject(source::getPhoneNo, target::getPhoneNo, target::setPhoneNo, copyPolicy);
		copyObject(source::getPhoto, target::getPhoto, target::setPhoto, copyPolicy);
		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);

		if (source.getDob() != null) {
			final Calendar dob = Calendar.getInstance();
			dob.setTime(source.getDob());

			final Calendar now = Calendar.getInstance();
			now.setTime(new Date());

			final int currentYear = now.get(Calendar.YEAR);
			final int doy = dob.get(Calendar.YEAR);
			int age = 0;
			if (currentYear > doy) {
				age = currentYear - doy;
				final int currentMonth = now.get(Calendar.MONTH);
				final int dom = dob.get(Calendar.MONTH);
				if (currentMonth < dom || (currentMonth == dom && now.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
					age -= 1;
				}
			}
			target.setAge(age);
		}
	}

	@Override
	protected void convertRelationshipInternal(final ServiceSupplierStaff source, final ServiceSupplierStaffDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceSupplierStaffRelationship.class)) {
			case SERVICE_CATEGORIES:
				copyRelationshipList(source::getServiceCategories, target::setServiceCategories, serviceCategoryConverter,
						relationshipExpression);
				break;
			case SERVICE_SUPPLIER_CLIENT:
				copyRelationship(source::getServiceSupplierClient, target::setServiceSupplierClient, serviceSupplierClientConverter,
						relationshipExpression);
				break;
			case SERVICE_ORDERS:
				copyRelationshipList(source::getServiceOrders, target::setServiceOrders, serviceOrderConverter, relationshipExpression);
				break;
			default:
				break;
		}
	}

	@Override
	protected ServiceSupplierStaff createFromInstance() {
		return new ServiceSupplierStaff();
	}

	@Override
	protected ServiceSupplierStaffDto createToInstance() {
		return new ServiceSupplierStaffDto();
	}
}

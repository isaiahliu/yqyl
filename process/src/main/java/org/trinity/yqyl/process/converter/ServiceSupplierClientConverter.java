package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.lookup.CompanyType;
import org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAccount;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAuditing;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientMaterial;

@Component
public class ServiceSupplierClientConverter extends AbstractLookupSupportObjectConverter<ServiceSupplierClient, ServiceSupplierClientDto> {

	private static enum ServiceSupplierClientRelationship {
		MATERIAL, BANK_ACCOUNT, SERVICE_INFOS, AUDITINGS, IMAGES, NA
	}

	@Autowired
	private IObjectConverter<ServiceSupplierClientMaterial, ServiceSupplierClientMaterialDto> serviceSupplierClientMaterialConverter;

	@Autowired
	private IObjectConverter<ServiceSupplierClientAccount, ServiceSupplierClientAccountDto> serviceSupplierClientAccountConverter;

	@Autowired
	private IObjectConverter<ServiceInfo, ServiceInfoDto> serviceInfoConverter;

	@Autowired
	private IObjectConverter<ServiceSupplierClientAuditing, ServiceSupplierClientAuditingDto> serviceSupplierClientAuditingConverter;

	@Autowired
	public ServiceSupplierClientConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceSupplierClientDto source, final ServiceSupplierClient target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getUserId, target::setUserId, copyPolicy);
		copyLookup(source::getStatus, target::getStatus, target::setStatus, ServiceSupplierClientStatus.class, copyPolicy);
		copyLookup(source::getType, target::getType, target::setType, CompanyType.class, copyPolicy);
		copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
		copyObject(source::getLogo, target::getLogo, target::setLogo, copyPolicy);
		copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
		copyObject(source::getName, target::getName, target::setName, copyPolicy);
		copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
		copyObject(source::getCategories, target::getServiceCategories, target::setServiceCategories, copyPolicy);
		copyObject(source::getContact, target::getContact, target::setContact, copyPolicy);
		copyObject(source::getContactPhone, target::getContactPhone, target::setContactPhone, copyPolicy);
		copyObject(source::getRegion, target::getRegion, target::setRegion, copyPolicy);
		copyObject(source::getServicePhone, target::getServicePhone, target::setServicePhone, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceSupplierClient source, final ServiceSupplierClientDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getUserId, target::getId, target::setId, copyPolicy);
		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
		copyMessage(source::getType, target::getType, target::setType, copyPolicy);
		copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
		copyObject(source::getName, target::getName, target::setName, copyPolicy);
		copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
		copyObject(source::getLogo, target::getLogo, target::setLogo, copyPolicy);
		copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
		copyObject(source::getServiceCategories, target::getCategories, target::setCategories, copyPolicy);
		copyObject(source::getContact, target::getContact, target::setContact, copyPolicy);
		copyObject(source::getContactPhone, target::getContactPhone, target::setContactPhone, copyPolicy);
		copyObject(source::getRegion, target::getRegion, target::setRegion, copyPolicy);
		copyObject(source::getServicePhone, target::getServicePhone, target::setServicePhone, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceSupplierClient source, final ServiceSupplierClientDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceSupplierClientRelationship.class)) {
			case BANK_ACCOUNT:
				copyRelationship(source::getBankAccount, target::setBankAccount, serviceSupplierClientAccountConverter,
						relationshipExpression);
				break;
			case MATERIAL:
				copyRelationship(source::getMaterial, target::setMaterial, serviceSupplierClientMaterialConverter, relationshipExpression);
				break;
			case SERVICE_INFOS:
				copyRelationshipList(source::getServiceInfos, target::setServiceInfos, serviceInfoConverter, relationshipExpression);
				break;
			case AUDITINGS:
				copyRelationshipList(source::getAuditings, target::setAuditings, serviceSupplierClientAuditingConverter,
						relationshipExpression);
				break;
			case IMAGES:
				copyObjectList(source::getImages, target::setImages);
				break;
			default:
				break;
		}
	}

	@Override
	protected ServiceSupplierClient createFromInstance() {
		return new ServiceSupplierClient();
	}

	@Override
	protected ServiceSupplierClientDto createToInstance() {
		return new ServiceSupplierClientDto();
	}
}

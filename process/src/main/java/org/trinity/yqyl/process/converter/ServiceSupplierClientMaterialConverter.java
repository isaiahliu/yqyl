package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientMaterial;

@Component
public class ServiceSupplierClientMaterialConverter
		extends AbstractLookupSupportObjectConverter<ServiceSupplierClientMaterial, ServiceSupplierClientMaterialDto> {
	private static enum ServiceSupplierClientMaterialRelationship {
		NA
	}

	@Autowired
	public ServiceSupplierClientMaterialConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final ServiceSupplierClientMaterialDto source, final ServiceSupplierClientMaterial target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getServiceSupplierClientId, target::setServiceSupplierClientId, copyPolicy);
		copyObject(source::getApplicationCopy, target::getApplicationCopy, target::setApplicationCopy, copyPolicy);
		copyObject(source::getApplicationNo, target::getApplicationNo, target::setApplicationNo, copyPolicy);
		copyObject(source::getBusinessLicenseCopy, target::getBusinessLicenseCopy, target::setBusinessLicenseCopy, copyPolicy);
		copyObject(source::getBusinessLicenseNo, target::getBusinessLicenseNo, target::setBusinessLicenseNo, copyPolicy);
		copyObject(source::getContractCopy, target::getContractCopy, target::setContractCopy, copyPolicy);
		copyObject(source::getContractNo, target::getContractNo, target::setContractNo, copyPolicy);
		copyObject(source::getCorporateCheckingCopy, target::getCorporateCheckingCopy, target::setCorporateCheckingCopy, copyPolicy);
		copyObject(source::getCorporateCheckingNo, target::getCorporateCheckingNo, target::setCorporateCheckingNo, copyPolicy);
		copyObject(source::getBusinessCertificateCopy, target::getBusinessCertificateCopy, target::setBusinessCertificateCopy, copyPolicy);
		copyObject(source::getBusinessCertificateNo, target::getBusinessCertificateNo, target::setBusinessCertificateNo, copyPolicy);
		copyObject(source::getLicenseCopy, target::getLicenseCopy, target::setLicenseCopy, copyPolicy);
		copyObject(source::getLicenseNo, target::getLicenseNo, target::setLicenseNo, copyPolicy);

		copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final ServiceSupplierClientMaterial source, final ServiceSupplierClientMaterialDto target,
			final CopyPolicy copyPolicy) {
		copyObject(source::getServiceSupplierClientId, target::getId, target::setId, copyPolicy);

		copyObject(source::getApplicationCopy, target::getApplicationCopy, target::setApplicationCopy, copyPolicy);
		copyObject(source::getApplicationNo, target::getApplicationNo, target::setApplicationNo, copyPolicy);
		copyObject(source::getBusinessLicenseCopy, target::getBusinessLicenseCopy, target::setBusinessLicenseCopy, copyPolicy);
		copyObject(source::getBusinessLicenseNo, target::getBusinessLicenseNo, target::setBusinessLicenseNo, copyPolicy);
		copyObject(source::getContractCopy, target::getContractCopy, target::setContractCopy, copyPolicy);
		copyObject(source::getContractNo, target::getContractNo, target::setContractNo, copyPolicy);
		copyObject(source::getCorporateCheckingCopy, target::getCorporateCheckingCopy, target::setCorporateCheckingCopy, copyPolicy);
		copyObject(source::getCorporateCheckingNo, target::getCorporateCheckingNo, target::setCorporateCheckingNo, copyPolicy);
		copyObject(source::getBusinessCertificateCopy, target::getBusinessCertificateCopy, target::setBusinessCertificateCopy, copyPolicy);
		copyObject(source::getBusinessCertificateNo, target::getBusinessCertificateNo, target::setBusinessCertificateNo, copyPolicy);
		copyObject(source::getLicenseCopy, target::getLicenseCopy, target::setLicenseCopy, copyPolicy);
		copyObject(source::getLicenseNo, target::getLicenseNo, target::setLicenseNo, copyPolicy);

		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final ServiceSupplierClientMaterial source, final ServiceSupplierClientMaterialDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(ServiceSupplierClientMaterialRelationship.class)) {
			default:
				break;
		}
	}

	@Override
	protected ServiceSupplierClientMaterial createFromInstance() {
		return new ServiceSupplierClientMaterial();
	}

	@Override
	protected ServiceSupplierClientMaterialDto createToInstance() {
		return new ServiceSupplierClientMaterialDto();
	}
}

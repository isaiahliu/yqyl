//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.RecordStatus;

/**
 * The persistent class for the service_supplier_client_material database table.
 *
 */
@Entity
@Table(name = "service_supplier_client_material")
@NamedQuery(name = "ServiceSupplierClientMaterial.findAll", query = "SELECT s FROM ServiceSupplierClientMaterial s")
public class ServiceSupplierClientMaterial extends AbstractAuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "service_supplier_client_id")
	private Long serviceSupplierClientId;

	@Column(name = "application_copy")
	private String applicationCopy;

	@Column(name = "application_no")
	private String applicationNo;

	@Column(name = "business_license_copy")
	private String businessLicenseCopy;

	@Column(name = "business_license_no")
	private String businessLicenseNo;

	@Column(name = "contract_copy")
	private String contractCopy;

	@Column(name = "contract_no")
	private String contractNo;

	@Column(name = "corporate_checking_copy")
	private String corporateCheckingCopy;

	@Column(name = "corporate_checking_no")
	private String corporateCheckingNo;

	@Column(name = "business_certificate_no")
	private String businessCertificateNo;

	@Column(name = "business_certificate_copy")
	private String businessCertificateCopy;

	@Column(name = "license_copy")
	private String licenseCopy;

	@Column(name = "license_no")
	private String licenseNo;

	private RecordStatus status;

	// bi-directional one-to-one association to ServiceSupplierClient
	@OneToOne
	@JoinColumn(name = "service_supplier_client_id")
	private ServiceSupplierClient serviceSupplierClient;

	public ServiceSupplierClientMaterial() {
	}

	public String getApplicationCopy() {
		return this.applicationCopy;
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public String getBusinessCertificateCopy() {
		return businessCertificateCopy;
	}

	public String getBusinessCertificateNo() {
		return businessCertificateNo;
	}

	public String getBusinessLicenseCopy() {
		return this.businessLicenseCopy;
	}

	public String getBusinessLicenseNo() {
		return this.businessLicenseNo;
	}

	public String getContractCopy() {
		return this.contractCopy;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public String getCorporateCheckingCopy() {
		return this.corporateCheckingCopy;
	}

	public String getCorporateCheckingNo() {
		return this.corporateCheckingNo;
	}

	public String getLicenseCopy() {
		return this.licenseCopy;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public ServiceSupplierClient getServiceSupplierClient() {
		return this.serviceSupplierClient;
	}

	public Long getServiceSupplierClientId() {
		return this.serviceSupplierClientId;
	}

	public RecordStatus getStatus() {
		return this.status;
	}

	public void setApplicationCopy(final String applicationCopy) {
		this.applicationCopy = applicationCopy;
	}

	public void setApplicationNo(final String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public void setBusinessCertificateCopy(final String businessCertificateCopy) {
		this.businessCertificateCopy = businessCertificateCopy;
	}

	public void setBusinessCertificateNo(final String businessCertificateNo) {
		this.businessCertificateNo = businessCertificateNo;
	}

	public void setBusinessLicenseCopy(final String businessLicenseCopy) {
		this.businessLicenseCopy = businessLicenseCopy;
	}

	public void setBusinessLicenseNo(final String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public void setContractCopy(final String contractCopy) {
		this.contractCopy = contractCopy;
	}

	public void setContractNo(final String contractNo) {
		this.contractNo = contractNo;
	}

	public void setCorporateCheckingCopy(final String corporateCheckingCopy) {
		this.corporateCheckingCopy = corporateCheckingCopy;
	}

	public void setCorporateCheckingNo(final String corporateCheckingNo) {
		this.corporateCheckingNo = corporateCheckingNo;
	}

	public void setLicenseCopy(final String licenseCopy) {
		this.licenseCopy = licenseCopy;
	}

	public void setLicenseNo(final String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public void setServiceSupplierClient(final ServiceSupplierClient serviceSupplierClient) {
		this.serviceSupplierClient = serviceSupplierClient;
	}

	public void setServiceSupplierClientId(final Long serviceSupplierClientId) {
		this.serviceSupplierClientId = serviceSupplierClientId;
	}

	public void setStatus(final RecordStatus status) {
		this.status = status;
	}

}

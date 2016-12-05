package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;
import org.trinity.common.dto.object.LookupDto;

public class ServiceSupplierClientDto extends AbstractBusinessDto {
    private LookupDto type;

    private String email;
    private String name;
    private String address;
    private String description;
    private String logo;
    private String contact;
    private String contactPhone;
    private String region;
    private String categories;
    private String servicePhone;
    private Double expectedPrice;

    private ServiceSupplierClientAccountDto bankAccount;
    private ServiceSupplierClientMaterialDto material;

    private List<ServiceInfoDto> serviceInfos;
    private List<ServiceSupplierClientAuditingDto> auditings;

    private List<String> images;

    public String getAddress() {
        return address;
    }

    public List<ServiceSupplierClientAuditingDto> getAuditings() {
        if (auditings == null) {
            auditings = new ArrayList<>();
        }
        return auditings;
    }

    public ServiceSupplierClientAccountDto getBankAccount() {
        return bankAccount;
    }

    public String getCategories() {
        return categories;
    }

    public String getContact() {
        return contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public Double getExpectedPrice() {
        return expectedPrice;
    }

    public List<String> getImages() {
        return images;
    }

    public String getLogo() {
        return logo;
    }

    public ServiceSupplierClientMaterialDto getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public List<ServiceInfoDto> getServiceInfos() {
        return serviceInfos;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public LookupDto getType() {
        return type;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAuditings(final List<ServiceSupplierClientAuditingDto> auditings) {
        this.auditings = auditings;
    }

    public void setBankAccount(final ServiceSupplierClientAccountDto bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setCategories(final String categories) {
        this.categories = categories;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setExpectedPrice(final Double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public void setImages(final List<String> images) {
        this.images = images;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }

    public void setMaterial(final ServiceSupplierClientMaterialDto material) {
        this.material = material;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public void setServiceInfos(final List<ServiceInfoDto> serviceInfos) {
        this.serviceInfos = serviceInfos;
    }

    public void setServicePhone(final String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public void setType(final LookupDto type) {
        this.type = type;
    }
}

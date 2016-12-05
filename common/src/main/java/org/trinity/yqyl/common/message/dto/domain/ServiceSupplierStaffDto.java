package org.trinity.yqyl.common.message.dto.domain;

import java.util.Date;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceSupplierStaffDto extends AbstractBusinessDto {
    private String comment;

    private Date dob;

    private String identityCard;

    private String name;

    private String phoneNo;

    private String photo;

    private Integer age;

    private ServiceSupplierClientDto serviceSupplierClient;

    private List<ServiceCategoryDto> serviceCategories;

    public Integer getAge() {
        return age;
    }

    public String getComment() {
        return comment;
    }

    public Date getDob() {
        return dob;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPhoto() {
        return photo;
    }

    public List<ServiceCategoryDto> getServiceCategories() {
        return serviceCategories;
    }

    public ServiceSupplierClientDto getServiceSupplierClient() {
        return serviceSupplierClient;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setIdentityCard(final String identityCard) {
        this.identityCard = identityCard;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public void setServiceCategories(final List<ServiceCategoryDto> serviceCategories) {
        this.serviceCategories = serviceCategories;
    }

    public void setServiceSupplierClient(final ServiceSupplierClientDto serviceSupplierClient) {
        this.serviceSupplierClient = serviceSupplierClient;
    }
}

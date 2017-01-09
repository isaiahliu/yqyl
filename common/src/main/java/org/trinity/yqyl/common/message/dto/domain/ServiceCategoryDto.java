package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.domain.AbstractBusinessDto;

public class ServiceCategoryDto extends AbstractBusinessDto {
    private String name;
    private String description;
    private List<ServiceCategoryDto> serviceSubCategories;
    private ServiceCategoryDto parent;
    private String image;

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public ServiceCategoryDto getParent() {
        return parent;
    }

    public List<ServiceCategoryDto> getServiceSubCategories() {
        if (serviceSubCategories == null) {
            serviceSubCategories = new ArrayList<>();
        }
        return serviceSubCategories;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParent(final ServiceCategoryDto parent) {
        this.parent = parent;
    }

    public void setServiceSubCategories(final List<ServiceCategoryDto> subServiceCategoryDto) {
        this.serviceSubCategories = subServiceCategoryDto;
    }
}

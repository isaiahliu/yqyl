package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.object.LookupDto;

public class ProvinceDto extends LookupDto {
    private List<CityDto> cities;

    public List<CityDto> getCities() {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        return cities;
    }

    public void setCities(final List<CityDto> cities) {
        this.cities = cities;
    }
}

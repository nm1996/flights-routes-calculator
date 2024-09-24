package com.route.flights.dto;

import com.route.flights.entity.City;
import lombok.*;

@Data
public class CityDto extends DTO {
    private String name;
    private CountryDto country;

    public CityDto() {
    }

    public CityDto(Long id) {
        super(id);
    }

    public CityDto(Long id, String name, CountryDto countryDto) {
        super(id);
        this.name = name;
        this.country = countryDto;
    }


}

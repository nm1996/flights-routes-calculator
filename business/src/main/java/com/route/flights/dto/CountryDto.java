package com.route.flights.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CountryDto extends DTO {
    private String name;

    public CountryDto() {
    }

    public CountryDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    public CountryDto(String name) {
        this.name = name;
    }

    public boolean equals(Object dto){
        if(dto instanceof CountryDto countryDto){
            return countryDto.getName().equals(this.name);
        }
        return false;
    }
}

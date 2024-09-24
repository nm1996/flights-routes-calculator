package com.route.flights.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
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
}

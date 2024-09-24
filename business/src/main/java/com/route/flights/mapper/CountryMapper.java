package com.route.flights.mapper;


import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper implements Mapper<CountryDto, Country> {

    @Override
    public CountryDto mapToDto(Country country) {
        CountryDto dto = new CountryDto();
        mapLong(country::getId, dto::setId);
        mapString(country::getName, dto::setName);
        return dto;
    }

    @Override
    public Country mapToEntity(CountryDto countryDto) {
        Country country = new Country();
        mapLong(countryDto::getId, country::setId);
        mapString(countryDto::getName, country::setName);
        return country;
    }
}

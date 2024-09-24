package com.route.flights.mapper;

import com.route.flights.dto.CityDto;
import com.route.flights.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CityMapper implements Mapper<CityDto, City> {

    private final CountryMapper countryMapper;

    @Override
    public CityDto mapToDto(City city) {
        CityDto dto = new CityDto();
        mapLong(city::getId, dto::setId);
        mapString(city::getName, dto::setName);
        dto.setCountry(countryMapper.mapToDto(city.getCountry()));
        return dto;
    }

    @Override
    public City mapToEntity(CityDto cityDto) {
        City city = new City();
        mapLong(cityDto::getId, city::setId);
        mapString(cityDto::getName, city::setName);
        city.setCountry(countryMapper.mapToEntity(cityDto.getCountry()));
        return city;
    }

}

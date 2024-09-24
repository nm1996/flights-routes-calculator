package com.route.flights.mapper;

import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.City;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CityMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        com.route.flights.mapper.CityMapper.class,
        com.route.flights.mapper.CountryMapper.class
})
class CityMapperTest {

    @Autowired
    CityMapper cityMapper;


    @Test
    void test_Entity_To_DTO() {
        Country country = new Country(1L, "Serbia");
        City city = new City(1L, "Belgrade", country);

        CityDto cityDto = cityMapper.mapToDto(city);
        assertEquals(1L, cityDto.getId());
        assertEquals("Belgrade", cityDto.getName());
        assertEquals("Serbia", cityDto.getCountry().getName());
    }

    @Test
    void test_DTO_to_Entity() {
        CountryDto countryDto =  new CountryDto(1L, "Serbia");
        CityDto dto = new CityDto(1L, "Belgrade", countryDto);

        City city = cityMapper.mapToEntity(dto);
        assertEquals("Belgrade", city.getName());
        assertEquals(1L, city.getId());
        assertEquals("Serbia", city.getCountry().getName());
        assertEquals(1L, city.getCountry().getId());
    }
}
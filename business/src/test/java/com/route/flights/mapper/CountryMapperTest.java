package com.route.flights.mapper;

import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CountryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CountryMapper.class})
class CountryMapperTest {

    @Autowired
    CountryMapper countryMapper;


    @Test
    void test_Entity_To_DTO(){
        Country country = new Country(1L, "Serbia");
        CountryDto dto = countryMapper.mapToDto(country);

        assertEquals("Serbia", dto.getName());
    }

    @Test
    void test_DTO_to_Entity(){
        CountryDto dto = new CountryDto("Serbia");
        Country country = countryMapper.mapToEntity(dto);

        assertEquals(null, country.getId());
        assertEquals("Serbia", country.getName());
    }

}
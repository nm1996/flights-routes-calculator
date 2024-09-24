package com.route.flights.mapper;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {
                com.route.flights.mapper.AirportMapper.class,
                com.route.flights.mapper.CityMapper.class,
                com.route.flights.mapper.CountryMapper.class
        }
)
class AirportMapperTest {

    @Autowired
    AirportMapper airportMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CountryMapper countryMapper;


    @Test
    void test_Entity_To_DTO(){
        Country country = new Country(1L, "Serbia");
        City city = new City(1L, "Belgrade", country);
        Airport airport = new Airport(
                1L,
                "Nikola Tesla",
                city,
                "BEG",
                "LYBE",
                44.81825555,
                20.30235943163158,
                102.0,
                2.0,
                "N",
                "Serbia/Belgrade",
                "airport",
                "test"
        );

        CountryDto countryDto = countryMapper.mapToDto(country);
        CityDto cityDto = cityMapper.mapToDto(city);
        AirportDto dto = airportMapper.mapToDto(airport);

        assertEquals(countryDto, cityDto.getCountry());
        assertEquals(cityDto, dto.getCity());
        assertEquals(countryDto, dto.getCity().getCountry());

        assertEquals("N", dto.getDst());
        assertEquals("BEG", dto.getIata());
        assertEquals("Serbia/Belgrade", dto.getTz());
        assertEquals(20.30235943163158, dto.getLongitude());
    }

    @Test
    void test_DTO_To_Entity(){
        CountryDto countryDto = new CountryDto( 1L, "Serbia");
        CityDto cityDto = new CityDto(1L, "Belgrade", countryDto);
        AirportDto dto = new AirportDto(
                1L,
                "Nikola Tesla",
                cityDto,
                "BEG",
                "LYBE",
                44.81825555,
                20.30235943163158,
                102.0,
                2.0,
                "N",
                "Serbia/Belgrade",
                "airport",
                "test"
        );

        Country country = countryMapper.mapToEntity(countryDto);
        City city = cityMapper.mapToEntity(cityDto);
        Airport airport = airportMapper.mapToEntity(dto);

        assertEquals(country, city.getCountry());
        assertEquals(city, airport.getCity());
        assertEquals(country, airport.getCity().getCountry());

        assertEquals("N", airport.getDst());
        assertEquals("BEG", airport.getIata());
        assertEquals("Serbia/Belgrade", airport.getTz());
        assertEquals(20.30235943163158, airport.getLongitude());
    }

}
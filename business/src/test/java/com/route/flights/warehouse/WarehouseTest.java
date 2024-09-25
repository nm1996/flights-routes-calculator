package com.route.flights.warehouse;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.warehouse.implementation.AirportWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CityWarehouse.class, CountryWarehouse.class, AirportWarehouse.class})
class WarehouseTest {

    @Autowired
    CityWarehouse cityWarehouse;

    @Autowired
    CountryWarehouse countryWarehouse;

    @Autowired
    AirportWarehouse airportWarehouse;

    @AfterEach
    void tearDown(){
        cityWarehouse.clear();
        countryWarehouse.clear();
        airportWarehouse.clear();
    }

    @Test
    void getCached() {
        Map<Long, CountryDto> countryDtoMap = new HashMap<>();
        Map<Long, AirportDto> airportDtoMap = new HashMap<>();
        Map<Long, CityDto> cityDtoMap = new HashMap<>();
        assertEquals(countryDtoMap, cityWarehouse.getCached());
        assertEquals(airportDtoMap, airportWarehouse.getCached());
        assertEquals(cityDtoMap, countryWarehouse.getCached());
    }

    @Test
    void putInCache() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        cityWarehouse.putInCache(cityDto);

        assertEquals(cityDto, cityWarehouse.getCached().get(1L));
    }

    @Test
    void addListToList() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.addListToCache(List.of(cityDto, cityDto1));

        assertEquals(2, cityWarehouse.getCached().size());
    }

    @Test
    void getById() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.putInCache(cityDto);
        cityWarehouse.putInCache(cityDto1);

        assertEquals(cityDto, cityWarehouse.getById(1L).get());
    }

    @Test
    void isMissed() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.putInCache(cityDto);

        assertTrue(cityWarehouse.isMissed(cityDto1));
    }

    @Test
    void isPresent() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        cityWarehouse.putInCache(cityDto);

        assertTrue(cityWarehouse.isPresent(cityDto));
    }

    @Test
    void test_Find_Matching_Record_In_Cache(){
        CountryDto countryDto = new CountryDto(1L, "Serbia");
        countryWarehouse.putInCache(new CountryDto(10L, "Serbia"));

        assertTrue(countryWarehouse.findCacheMatch(countryDto).isPresent());

        CityDto cityDto = new CityDto(10L, "Belgrade", countryDto);
        cityWarehouse.putInCache(
                new CityDto(
                        20L, "Belgrade",
                        new CountryDto(2L, "Serbia")
                )
        );

        assertTrue(cityWarehouse.findCacheMatch(cityDto).isPresent());

        AirportDto airportDto = new AirportDto(
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

        airportWarehouse.putInCache( new AirportDto(
                        null,
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
                )
        );

        assertTrue(airportWarehouse.findCacheMatch(airportDto).isPresent());
    }


}
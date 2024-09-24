package com.route.flights.warehouse;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.warehouse.implementation.AirportWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CityWarehouse.class, CountryWarehouse.class, AirportWarehouse.class})
class WarehouseTest {

    @Autowired
    CityWarehouse cityWarehouse;

    @Autowired
    CountryWarehouse countryWarehouse;

    @Autowired
    AirportWarehouse airportWarehouse;

    @Test
    void getCached() {
        List<CountryDto> countryDtoList = new ArrayList<>();
        List<AirportDto> airportDtoList = new ArrayList<>();
        List<CityDto> cityDtoList = new ArrayList<>();
        assertEquals(cityDtoList, cityWarehouse.getCached());
        assertEquals(airportDtoList, airportWarehouse.getCached());
        assertEquals(countryDtoList, countryWarehouse.getCached());
    }

    @Test
    void addToList() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        cityWarehouse.addToList(cityDto);

        assertEquals(cityDto, cityWarehouse.getCached().getFirst());

        cityWarehouse.clear();
    }

    @Test
    void addListToList() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.addListToList(List.of(cityDto, cityDto1));

        assertEquals(2, cityWarehouse.getCached().size());

        cityWarehouse.clear();
    }

    @Test
    void getById() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.addToList(cityDto);
        cityWarehouse.addToList(cityDto1);

        assertEquals(cityDto, cityWarehouse.getById(1L).get());

        cityWarehouse.clear();
    }

    @Test
    void isMissed() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        CityDto cityDto1 = new CityDto(2L, "Nis", new CountryDto(2L, "Serbia"));
        cityWarehouse.addToList(cityDto);

        assertTrue(cityWarehouse.isMissed(cityDto1));

        cityWarehouse.clear();
    }

    @Test
    void isPresent() {
        CityDto cityDto = new CityDto(1L, "Belgrade", new CountryDto(1L, "Serbia"));
        cityWarehouse.addToList(cityDto);

        assertTrue(cityWarehouse.isPresent(cityDto));

        cityWarehouse.clear();
    }
}
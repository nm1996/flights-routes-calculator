package com.route.flights.processor;

import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.repository.CountryRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {
                CountryProcessor.class,
                CountryWarehouse.class,
                CountryMapper.class,
                GlobalWarehouse.class
        }
)
class CountryProcessorTest {

    CountryProcessor countryProcessor;

    @MockBean
    CountryRepository countryRepository;
    @Mock
    CountryMapper countryMapper;
    @MockBean
    GlobalWarehouse globalWarehouse;
    @Mock
    CountryWarehouse countryWarehouse;

    @BeforeEach
    void setUp() {
        countryProcessor = new CountryProcessor(
                countryRepository,
                countryMapper,
                globalWarehouse
        );
    }


    @Test
    void processImportCountries() {
        Airport airport = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        List<Airport> airportList = List.of(airport);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(countryRepository.save(country)).thenReturn(country);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);

        var resultList = countryProcessor.processImportCountries(airportList);

        assertEquals(resultList, List.of(countryDto));

        Mockito.verify(countryRepository).save(country);
        Mockito.verify(countryWarehouse).addToList(countryDto);
    }
}
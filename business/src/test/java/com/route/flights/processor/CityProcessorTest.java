package com.route.flights.processor;

import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CityMapper;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.repository.CityRepository;
import com.route.flights.repository.CountryRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        CityProcessor.class
})
class CityProcessorTest {

    CityProcessor cityProcessor;

    @MockBean
    CityRepository cityRepository;
    @MockBean
    CityMapper cityMapper;
    @MockBean
    CountryMapper countryMapper;
    @MockBean
    GlobalWarehouse globalWarehouse;
    @MockBean
    CountryWarehouse countryWarehouse;
    @MockBean
    CityWarehouse cityWarehouse;
    @MockBean
    CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        cityProcessor = new CityProcessor(
                cityRepository, cityMapper, countryMapper, globalWarehouse, countryRepository
        );
    }

    @Test
    void processImportCities() {
        Airport airport = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);

        Mockito.when(countryWarehouse.isMissed(countryDto)).thenReturn(false);

        var resultList = cityProcessor.processImportCities(airportList);

        assertEquals(resultList, List.of(cityDto));

        Mockito.verify(cityRepository).save(city);
        Mockito.verify(cityWarehouse).addToList(cityDto);
    }
}
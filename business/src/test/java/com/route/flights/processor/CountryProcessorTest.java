package com.route.flights.processor;

import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.repository.CountryRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    void test_Process_Import_No_Cached_Records() {
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
        Mockito.when(countryWarehouse.isMissed(countryDto)).thenReturn(true);

        var resultList = countryProcessor.processImportCountries(airportList);

        assertEquals(resultList, List.of(countryDto));

        Mockito.verify(countryRepository).save(country);
        Mockito.verify(countryWarehouse).addToList(countryDto);
    }

    @Test
    void test_Process_Import_Cached_Records() {
        Airport airport = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        List<Airport> airportList = List.of(airport);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);
        Mockito.when(countryWarehouse.isMissed(countryDto)).thenReturn(false);

        var resultList = countryProcessor.processImportCountries(airportList);

        assertEquals(resultList, List.of());

        Mockito.verifyNoInteractions(countryRepository);
        Mockito.verify(globalWarehouse, Mockito.times(2)).getCountryWarehouse();
        Mockito.verify(countryWarehouse).isMissed(countryDto);
        Mockito.verifyNoMoreInteractions(countryWarehouse);
    }
}
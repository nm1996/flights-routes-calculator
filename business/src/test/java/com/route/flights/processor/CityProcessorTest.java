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
import java.util.Optional;

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
    void test_Process_Import_No_Cached_Records() {
        Airport airport = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport);
        Optional<CountryDto> optionalCountryDto = Optional.of(countryDto);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(cityWarehouse.isMissed(cityDto)).thenReturn(true);

        Mockito.when(countryWarehouse.findCacheMatch(countryDto)).thenReturn(optionalCountryDto);


        var resultList = cityProcessor.processImportCities(airportList);

        assertEquals(resultList, List.of(cityDto));

        Mockito.verifyNoInteractions(countryRepository);
        Mockito.verify(cityRepository).save(city);
        Mockito.verify(cityWarehouse).addToList(cityDto);
    }

    @Test
    void test_Process_Import_Cached_Records() {
        Airport airport = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(cityWarehouse.isMissed(cityDto)).thenReturn(false);

        var resultList = cityProcessor.processImportCities(airportList);

        assertEquals(resultList, List.of());

        Mockito.verifyNoInteractions(cityRepository);
        Mockito.verify(globalWarehouse, Mockito.times(2)).getCityWarehouse();
        Mockito.verify(cityWarehouse).isMissed(cityDto);
        Mockito.verifyNoMoreInteractions(globalWarehouse);
    }


    @Test
    void test_Process_Import_Cached_Country_Records() {
        Airport airport = Mockito.mock(Airport.class);
        Airport airport2 = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        City city2 = Mockito.mock(City.class);
        Country country = Mockito.mock(Country.class);
        Country country2 = Mockito.mock(Country.class);
        CountryDto countryDto = Mockito.mock(CountryDto.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        CountryDto countryDto2 = Mockito.mock(CountryDto.class);
        CityDto cityDto2 = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport, airport2);
        Optional<CountryDto> optionalCountryDto = Optional.of(countryDto);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(airport2.getCity()).thenReturn(city2);
        Mockito.when(city.getCountry()).thenReturn(country);
        Mockito.when(city2.getCountry()).thenReturn(country2);
        Mockito.when(countryRepository.save(country2)).thenReturn(country2);
        Mockito.when(countryMapper.mapToDto(country)).thenReturn(countryDto);
        Mockito.when(countryMapper.mapToDto(country2)).thenReturn(countryDto2);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(cityMapper.mapToDto(city2)).thenReturn(cityDto2);
        Mockito.when(globalWarehouse.getCountryWarehouse()).thenReturn(countryWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(cityWarehouse.isMissed(cityDto)).thenReturn(true);
        Mockito.when(cityWarehouse.isMissed(cityDto2)).thenReturn(true);
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        Mockito.when(cityRepository.save(city2)).thenReturn(city2);

        Mockito.when(countryWarehouse.findCacheMatch(countryDto)).thenReturn(optionalCountryDto);
        Mockito.when(countryWarehouse.findCacheMatch(countryDto2)).thenReturn(Optional.empty());


        var resultList = cityProcessor.processImportCities(airportList);

        assertEquals(resultList, List.of(cityDto, cityDto2));

        Mockito.verify(globalWarehouse, Mockito.times(3)).getCityWarehouse();
        Mockito.verify(globalWarehouse, Mockito.times(3)).getCountryWarehouse();
        Mockito.verify(cityWarehouse).isMissed(cityDto);
        Mockito.verify(cityWarehouse).isMissed(cityDto2);
        Mockito.verify(cityRepository).save(city);
        Mockito.verify(cityRepository).save(city2);
        Mockito.verify(countryWarehouse).findCacheMatch(countryDto);
        Mockito.verify(countryWarehouse).findCacheMatch(countryDto2);
        Mockito.verify(countryWarehouse).addToList(countryDto2);
        Mockito.verifyNoMoreInteractions(countryWarehouse);
        Mockito.verify(cityWarehouse).addToList(cityDto);
        Mockito.verify(cityWarehouse).addToList(cityDto2);
    }
}
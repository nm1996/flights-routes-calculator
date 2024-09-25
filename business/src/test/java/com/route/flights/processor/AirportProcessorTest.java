package com.route.flights.processor;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.mapper.AirportMapper;
import com.route.flights.mapper.CityMapper;
import com.route.flights.repository.AirportRepository;
import com.route.flights.repository.CityRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import com.route.flights.warehouse.implementation.AirportWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AirportProcessor.class})
class AirportProcessorTest {

    AirportProcessor airportProcessor;

    @MockBean
    CityRepository cityRepository;
    @MockBean
    CityMapper cityMapper;
    @MockBean
    AirportMapper airportMapper;
    @MockBean
    GlobalWarehouse globalWarehouse;
    @MockBean
    AirportWarehouse airportWarehouse;
    @MockBean
    CityWarehouse cityWarehouse;
    @MockBean
    AirportRepository airportRepository;

    @BeforeEach
    void setUp() {
        airportProcessor = new AirportProcessor(
                cityRepository, airportRepository, airportMapper, cityMapper, globalWarehouse
        );
    }

    @Test
    void test_Process_Import_No_Cached_Records() {
        Airport airport = Mockito.mock(Airport.class);
        AirportDto airportDto = Mockito.mock(AirportDto.class);
        City city = Mockito.mock(City.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport);
        Optional<CityDto> optionalCityDto = Optional.of(cityDto);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
        Mockito.when(airportMapper.mapToDto(airport)).thenReturn(airportDto);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(globalWarehouse.getAirportWarehouse()).thenReturn(airportWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(airportWarehouse.isMissed(airportDto)).thenReturn(true);

        Mockito.when(cityWarehouse.findCacheMatch(cityDto)).thenReturn(optionalCityDto);


        var resultList = airportProcessor.processAirports(airportList);

        assertEquals(resultList, List.of(airportDto));

        Mockito.verifyNoInteractions(cityRepository);
        Mockito.verify(airportRepository).save(airport);
        Mockito.verify(airportWarehouse).putInCache(airportDto);
    }

    @Test
    void test_Process_Import_Cached_Records() {
        Airport airport = Mockito.mock(Airport.class);
        AirportDto airportDto = Mockito.mock(AirportDto.class);
        City city = Mockito.mock(City.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(airportMapper.mapToDto(airport)).thenReturn(airportDto);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(globalWarehouse.getAirportWarehouse()).thenReturn(airportWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(airportWarehouse.isMissed(airportDto)).thenReturn(false);

        var resultList = airportProcessor.processAirports(airportList);

        assertEquals(resultList, List.of());

        Mockito.verifyNoInteractions(airportRepository);
        Mockito.verify(globalWarehouse, Mockito.times(2)).getAirportWarehouse();
        Mockito.verify(airportWarehouse).isMissed(airportDto);
        Mockito.verifyNoMoreInteractions(globalWarehouse);
    }


    @Test
    void test_Process_Import_Cached_City_Records() {
        Airport airport = Mockito.mock(Airport.class);
        Airport airport2 = Mockito.mock(Airport.class);
        City city = Mockito.mock(City.class);
        City city2 = Mockito.mock(City.class);
        AirportDto airportDto = Mockito.mock(AirportDto.class);
        AirportDto airportDto2 = Mockito.mock(AirportDto.class);
        CityDto cityDto = Mockito.mock(CityDto.class);
        CityDto cityDto2 = Mockito.mock(CityDto.class);
        List<Airport> airportList = List.of(airport, airport2);
        Optional<CityDto> optionalCityDto = Optional.of(cityDto);

        Mockito.when(airport.getCity()).thenReturn(city);
        Mockito.when(airport2.getCity()).thenReturn(city2);
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
        Mockito.when(airportRepository.save(airport2)).thenReturn(airport2);
        Mockito.when(cityRepository.save(city2)).thenReturn(city2);
        Mockito.when(airportMapper.mapToDto(airport)).thenReturn(airportDto);
        Mockito.when(airportMapper.mapToDto(airport2)).thenReturn(airportDto2);
        Mockito.when(cityMapper.mapToDto(city)).thenReturn(cityDto);
        Mockito.when(cityMapper.mapToDto(city2)).thenReturn(cityDto2);
        Mockito.when(globalWarehouse.getAirportWarehouse()).thenReturn(airportWarehouse);
        Mockito.when(globalWarehouse.getCityWarehouse()).thenReturn(cityWarehouse);
        Mockito.when(airportWarehouse.isMissed(airportDto)).thenReturn(true);
        Mockito.when(airportWarehouse.isMissed(airportDto2)).thenReturn(true);
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        Mockito.when(cityRepository.save(city2)).thenReturn(city2);

        Mockito.when(cityWarehouse.findCacheMatch(cityDto)).thenReturn(optionalCityDto);
        Mockito.when(cityWarehouse.findCacheMatch(cityDto2)).thenReturn(Optional.empty());


        var resultList = airportProcessor.processAirports(airportList);

        assertEquals(resultList, List.of(airportDto, airportDto2));

        Mockito.verify(globalWarehouse, Mockito.times(3)).getAirportWarehouse();
        Mockito.verify(globalWarehouse, Mockito.times(3)).getCityWarehouse();
        Mockito.verify(airportWarehouse).isMissed(airportDto);
        Mockito.verify(airportWarehouse).isMissed(airportDto2);
        Mockito.verify(airportRepository).save(airport);
        Mockito.verify(airportRepository).save(airport2);
        Mockito.verify(cityRepository).save(city2);
        Mockito.verify(cityWarehouse).findCacheMatch(cityDto);
        Mockito.verify(cityWarehouse).findCacheMatch(cityDto2);
        Mockito.verify(cityWarehouse).putInCache(cityDto2);
        Mockito.verifyNoMoreInteractions(cityWarehouse);
        Mockito.verify(airportWarehouse).putInCache(airportDto);
        Mockito.verify(airportWarehouse).putInCache(airportDto2);
    }

}
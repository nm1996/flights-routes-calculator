//package com.route.flights.service.implementation;
//
//import com.route.flights.binder.implementation.AirportBinder;
//import com.route.flights.binder.implementation.CityBinder;
//import com.route.flights.binder.implementation.CountryBinder;
//import com.route.flights.dto.AirportDto;
//import com.route.flights.entity.Airport;
//import com.route.flights.importer.AirportImporter;
//import com.route.flights.mapper.AirportMapper;
//import com.route.flights.mapper.CityMapper;
//import com.route.flights.mapper.CountryMapper;
//import com.route.flights.repository.AirportRepository;
//import com.route.flights.service.AirportService;
//import com.route.flights.util.FileReader;
//import com.route.flights.util.StringManipulator;
//import com.route.flights.warehouse.implementation.AirportWarehouse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(
//        classes = {
//                AirportRepository.class,
//                AirportWarehouse.class,
//                AirportMapper.class,
//                AirportImporter.class,
//                AirportBinder.class,
//                CityBinder.class,
//                CountryBinder.class,
//                CityMapper.class,
//                CountryMapper.class,
//                FileReader.class,
//                StringManipulator.class
//        }
//)
//class AirportServiceImplTest {
//
//    AirportService airportService;
//
//    @Mock
//    AirportRepository airportRepository;
//    @Mock
//    AirportWarehouse airportWarehouse;
//    @Mock
//    AirportImporter airportImporter;
//    @Mock
//    AirportMapper airportMapper;
//
//    @BeforeEach
//    void setUp(){
//        airportService = new AirportServiceImpl(
//                airportRepository,
//                airportWarehouse,
//                airportImporter,
//                airportMapper
//        );
//    }
//
//    @Test
//    void test_Mock_Get_All_From_Warehouse(){
//        AirportDto dto = Mockito.mock(AirportDto.class);
//        List<AirportDto> dtoList = List.of(dto);
//
//        Mockito.when(airportWarehouse.getCached()).thenReturn(dtoList);
//
//        List<AirportDto> resultList = airportService.getAll();
//
//        assertEquals(resultList, dtoList);
//    }
//
//    @Test
//    void test_Mock_Get_All_From_Files(){
//        AirportDto dto = Mockito.mock(AirportDto.class);
//        Airport airport = Mockito.mock(Airport.class);
//        List<AirportDto> mockDtoList = List.of(dto);
//
//        Mockito.when(airportWarehouse.getCached()).thenReturn(new ArrayList<>());
//        Mockito.when(airportImporter.readFromStaticFile()).thenReturn(mockDtoList);
//        Mockito.when(airportMapper.mapToEntity(dto)).thenReturn(airport);
//        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
//        Mockito.when(airportMapper.mapToDto(airport)).thenReturn(dto);
//
//        List<AirportDto> result = airportService.getAll();
//
//        assertEquals(result, mockDtoList);
//        assertEquals(1, result.size());
//        Mockito.verify(airportWarehouse).addListToList(result);
//    }
//}
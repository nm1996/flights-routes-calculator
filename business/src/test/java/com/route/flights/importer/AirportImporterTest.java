//package com.route.flights.importer;
//
//import com.route.flights.binder.implementation.AirportBinder;
//import com.route.flights.binder.implementation.CityBinder;
//import com.route.flights.binder.implementation.CountryBinder;
//import com.route.flights.dto.AirportDto;
//import com.route.flights.repository.CityRepository;
//import com.route.flights.util.FileReader;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = {
//        AirportImporter.class,
//        AirportBinder.class,
//        CityBinder.class,
//        CountryBinder.class,
//        FileReader.class,
//})
//class AirportImporterTest {
//
//    @Autowired
//    AirportImporter importer;
//
//
//    @Test
//    void test_Import_Proper_File() {
//        List<AirportDto> dtoList = importer.readFromFile("airports.txt");
//        assertEquals(7184 , dtoList.size());
//        assertEquals("Shenyang Dongta Airport", dtoList.getLast().getName());
//    }
//
//    @Test
//    void test_Read_Static_File() {
//        List<AirportDto> dtoList = importer.readFromStaticFile();
//        assertEquals(7184 , dtoList.size());
//        assertEquals("Shenyang Dongta Airport", dtoList.getLast().getName());
//    }
//
//    @Test
//    void test_Exception_Raise() {
//        List<AirportDto> dtoList = importer.readFromFile("NOT_EXISTING.txt");
//        assertEquals(0, dtoList.size());
//    }
//}
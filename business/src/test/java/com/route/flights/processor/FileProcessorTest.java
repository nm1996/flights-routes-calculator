package com.route.flights.processor;

import com.route.flights.binder.implementation.AirportBinder;
import com.route.flights.binder.implementation.CityBinder;
import com.route.flights.binder.implementation.CountryBinder;
import com.route.flights.mapper.AirportMapper;
import com.route.flights.mapper.CityMapper;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.util.FileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        CityBinder.class,
        CountryBinder.class,
        AirportBinder.class,
        FileReader.class,
        CityMapper.class,
        CountryMapper.class,
        AirportMapper.class,
        FileProcessor.class
})
class FileProcessorTest {

    @Autowired
    FileProcessor fileProcessor;

    @Test
    void getAirportsFromFile() {
        var resultList = fileProcessor.getAirportsFromFile("airports.txt");
        assertTrue(resultList.size()>5000);
    }

    @Test
    void test_Wrong_File(){
        var resultList = fileProcessor.getAirportsFromFile("WRONG_FILE.txt");
        assertEquals(0, resultList.size());
    }
}
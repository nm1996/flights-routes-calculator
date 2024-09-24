package com.route.flights.binder;

import com.route.flights.binder.exception.TextBinderMismatchArgs;
import com.route.flights.binder.implementation.AirportBinder;
import com.route.flights.binder.implementation.CityBinder;
import com.route.flights.binder.implementation.CountryBinder;
import com.route.flights.dto.AirportDto;
import com.route.flights.util.FileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        AirportBinder.class,
        CountryBinder.class,
        CityBinder.class,
        FileReader.class
})
class BinderTest {

    @Autowired
    AirportBinder airportBinder;

    @Autowired
    CountryBinder countryBinder;

    @Autowired
    CityBinder cityBinder;

    @Autowired
    FileReader fileReader;


    @Test
    void test_Proper_Binding_Airports_Text_File() throws IOException {
        List<String[]> results = fileReader.readFileFromStaticResources("airports.txt");
        List<AirportDto> airportDtoList = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            AirportDto airportDto = airportBinder.bindFromTextualSourceFile(results.get(i));
            airportDtoList.add(airportDto);
        }

        assertTrue(!airportDtoList.isEmpty());
        assertEquals(20, airportDtoList.size());

        AirportDto firstAirport = airportDtoList.getFirst();
        assertEquals(1, firstAirport.getId());
        assertEquals("Goroka Airport", firstAirport.getName());
        assertEquals("OurAirports", firstAirport.getSource());
        assertEquals("GKA", firstAirport.getIata());
    }

    @Test
    void test_Bind_Every_File_Record() throws IOException{
        List<String[]> results = fileReader.readFileFromStaticResources("airports.txt");
        List<AirportDto> airportDtoList = results.stream()
                .map(airportBinder::bindFromTextualSourceFile)
                .toList();

        assertEquals(7184, airportDtoList.size());
        assertEquals(12057, airportDtoList.getLast().getId());
    }

    @Test
    void test_Error_Binding_Airport(){
        assertThrows(
                TextBinderMismatchArgs.class,
                () -> airportBinder.bindFromTextualSourceFile("1", "2", "1","1","1","1","1","1","1","1")
        );
    }

    @Test
    void test_Error_Binding_City(){
        assertThrows(
                TextBinderMismatchArgs.class,
                () -> cityBinder.bindFromTextualSourceFile("")
        );
    }

    @Test
    void test_Error_Binding_Country(){
        assertThrows(
                TextBinderMismatchArgs.class,
                () -> countryBinder.bindFromTextualSourceFile()
        );
    }

}
package com.route.flights.util;

import com.route.flights.util.FileReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {com.route.flights.util.FileReader.class})
class FileReaderTest {

    @Autowired
    FileReader fileReader;

    @Test
    void test_Read_Airports_Properly() throws IOException {
        List<String[]> results = fileReader.readFileFromStaticResources("airports.txt");
        assertTrue(!results.isEmpty());
        assertEquals(7184, results.size());
    }

    @Test
    void test_No_File_Present_Read() throws IOException{
        assertThrows(
                IOException.class,
                () ->  fileReader.readFileFromStaticResources("notExistingFile.txt")
        );
    }
}
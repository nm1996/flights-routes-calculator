package com.route.flights.processor;

import com.route.flights.binder.implementation.AirportBinder;
import com.route.flights.dto.AirportDto;
import com.route.flights.entity.Airport;
import com.route.flights.mapper.AirportMapper;
import com.route.flights.util.FileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileProcessor {

    private final AirportBinder airportBinder;
    private final AirportMapper airportMapper;
    private final FileReader fileReader;

    public List<Airport> getAirportsFromFile(String fileName){
        return readAndMapAirportsFromFile(fileName).stream()
                .map(airportMapper::mapToEntity)
                .toList();
    }

    private List<AirportDto> readAndMapAirportsFromFile(String fileName) {
        List<String[]> fileRecords;
        try {
            log.info("Reading airport records from file: {}", fileName);
            fileRecords = fileReader.readFileFromStaticResources(fileName);
        } catch (IOException e) {
            log.error("Error appear when reading from file: {} with message: {}", fileName, e.getMessage());
            return List.of();
        }

        return fileRecords.stream()
                .map(airportBinder::bindFromTextualSourceFile)
                .toList();
    }
}

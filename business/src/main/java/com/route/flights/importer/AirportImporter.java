package com.route.flights.importer;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.processor.AirportProcessor;
import com.route.flights.processor.CityProcessor;
import com.route.flights.processor.CountryProcessor;
import com.route.flights.processor.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class AirportImporter {

    private final FileProcessor fileProcessor;
    private final CountryProcessor countryProcessor;
    private final CityProcessor cityProcessor;
    private final AirportProcessor airportProcessor;
    private final ExecutorService executorService;

    public AirportImporter(@Value("${importer.thread.pool.size:10}") int threadsCount,
                           FileProcessor fileProcessor,
                           CountryProcessor countryProcessor,
                           CityProcessor cityProcessor,
                           AirportProcessor airportProcessor) {
        this.fileProcessor = fileProcessor;
        this.countryProcessor = countryProcessor;
        this.cityProcessor = cityProcessor;
        this.airportProcessor = airportProcessor;
        this.executorService = Executors.newFixedThreadPool(threadsCount);
    }

    public List<AirportDto> readFromFile(String fileName) {
        return readAndStoreFile(fileName);
    }

    public List<AirportDto> readFromStaticFile() {
        return readAndStoreFile("airports.txt");
    }


    private List<AirportDto> readAndStoreFile(String fileName) {
        List<Airport> airportsFromFile = fileProcessor.getAirportsFromFile(fileName);

        CompletableFuture<List<CountryDto>> futureCountries = CompletableFuture.supplyAsync(
                () -> countryProcessor.processImportCountries(airportsFromFile), executorService
        );

        CompletableFuture<List<CityDto>> futureCities = CompletableFuture.supplyAsync(
                () -> cityProcessor.processImportCities(airportsFromFile), executorService
        );

        CompletableFuture<List<AirportDto>> futureAirports = CompletableFuture.supplyAsync(
                () -> airportProcessor.processAirports(airportsFromFile), executorService
        );

        // Waiting for all threads to finish
        CompletableFuture.allOf(futureCountries, futureCities, futureAirports).join();

        return futureAirports.join();
    }

}

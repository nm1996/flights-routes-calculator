package com.route.flights.processor;

import com.route.flights.dto.AirportDto;
import com.route.flights.entity.Airport;
import com.route.flights.mapper.AirportMapper;
import com.route.flights.mapper.CityMapper;
import com.route.flights.repository.AirportRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AirportProcessor {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final CityMapper cityMapper;
    private final GlobalWarehouse globalWarehouse;


    public List<AirportDto> processAirports(List<Airport> airports) {
        return airports.stream()
                .peek(airport -> {
                    var dtoCity = cityMapper.mapToDto(airport.getCity());
                    if (globalWarehouse.getCityWarehouse().isMissed(dtoCity)) {
                        globalWarehouse.getCityWarehouse().putInCache(dtoCity);
                    }
                    airport.setCity(cityMapper.mapToEntity(dtoCity));
                })
                .map(airportRepository::save)
                .map(airportMapper::mapToDto)
                .peek(globalWarehouse.getAirportWarehouse()::putInCache)
                .toList();
    }
}

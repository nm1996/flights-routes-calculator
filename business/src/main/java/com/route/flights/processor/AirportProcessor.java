package com.route.flights.processor;

import com.route.flights.dto.AirportDto;
import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.mapper.AirportMapper;
import com.route.flights.mapper.CityMapper;
import com.route.flights.repository.AirportRepository;
import com.route.flights.repository.CityRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AirportProcessor {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final CityMapper cityMapper;
    private final GlobalWarehouse globalWarehouse;


    public List<AirportDto> processAirports(List<Airport> airports) {
        return airports.stream()
                .distinct()
                .filter(this::isNotCached)
                .peek(this::processCityForAirport)
                .map(airportRepository::save)
                .map(airportMapper::mapToDto)
                .peek(globalWarehouse.getAirportWarehouse()::putInCache)
                .toList();
    }

    private boolean isNotCached(Airport airport){
        return globalWarehouse.getAirportWarehouse().isMissed(airportMapper.mapToDto(airport));
    }

    private void processCityForAirport(Airport airport){
        var city = airport.getCity();
        var cityDto = cityMapper.mapToDto(city);
        var optionalCacheMatch = globalWarehouse.getCityWarehouse()
                .findCacheMatch(cityDto);

        CityDto entityForStore;
        if(optionalCacheMatch.isEmpty()){
            var storedCity = cityRepository.save(city);
            entityForStore = cityMapper.mapToDto(storedCity);
            globalWarehouse.getCityWarehouse().putInCache(entityForStore);
        }else{
            entityForStore = optionalCacheMatch.get();
        }

        airport.setCity(cityMapper.mapToEntity(entityForStore));
    }
}

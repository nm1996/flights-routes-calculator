package com.route.flights.processor;

import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.Country;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.repository.CountryRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CountryProcessor {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final GlobalWarehouse globalWarehouse;

    public List<CountryDto> processImportCountries(List<Airport> airports) {
        return airports.stream()
                .map(airport -> airport.getCity().getCountry())
                .distinct()
                .filter(this::isNotCached)
                .map(countryRepository::save)
                .map(countryMapper::mapToDto)
                .peek(globalWarehouse.getCountryWarehouse()::addToList)
                .toList();
    }

    private boolean isNotCached(Country country){
        return globalWarehouse.getCountryWarehouse().isMissed(countryMapper.mapToDto(country));
    }
}

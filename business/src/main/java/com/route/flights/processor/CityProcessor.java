package com.route.flights.processor;

import com.route.flights.dto.CityDto;
import com.route.flights.dto.CountryDto;
import com.route.flights.entity.Airport;
import com.route.flights.entity.City;
import com.route.flights.mapper.CityMapper;
import com.route.flights.mapper.CountryMapper;
import com.route.flights.repository.CityRepository;
import com.route.flights.repository.CountryRepository;
import com.route.flights.warehouse.GlobalWarehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CityProcessor {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountryMapper countryMapper;
    private final GlobalWarehouse globalWarehouse;
    private final CountryRepository countryRepository;

    public List<CityDto> processImportCities(List<Airport> airports) {
        return airports.stream()
                .map(Airport::getCity)
                .distinct()
                .peek(this::processCountryForCity)
                .map(cityRepository::save)
                .map(cityMapper::mapToDto)
                .peek(globalWarehouse.getCityWarehouse()::addToList)
                .toList();
    }


    private void processCountryForCity(City city) {
        var country = city.getCountry();
        var countryDto = countryMapper.mapToDto(country);
        var optionalCacheMatch = globalWarehouse.getCountryWarehouse()
                .findCacheMatch(countryDto);

        CountryDto entityForStore;
        if(optionalCacheMatch.isEmpty()){
            var storedCountry = countryRepository.save(country);
            entityForStore = countryMapper.mapToDto(storedCountry);
            globalWarehouse.getCountryWarehouse().addToList(entityForStore);
        }else{
            entityForStore = optionalCacheMatch.get();
        }

        city.setCountry(countryMapper.mapToEntity(entityForStore));
    }
}

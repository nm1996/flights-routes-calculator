package com.route.flights.warehouse.implementation;

import com.route.flights.dto.CountryDto;
import com.route.flights.warehouse.Warehouse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class CountryWarehouse implements Warehouse<CountryDto, Long> {


    private final Map<Long, CountryDto> cache = new HashMap<>();

    @Override
    public Map<Long, CountryDto> getCached() {
        return cache;
    }
}

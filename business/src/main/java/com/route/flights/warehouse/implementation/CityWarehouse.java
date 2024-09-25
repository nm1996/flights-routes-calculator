package com.route.flights.warehouse.implementation;

import com.route.flights.dto.CityDto;
import com.route.flights.warehouse.Warehouse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CityWarehouse implements Warehouse<CityDto, Long> {

    private final Map<Long, CityDto> cache = new HashMap<>();

    @Override
    public Map<Long, CityDto> getCached() {
        return cache;
    }
}

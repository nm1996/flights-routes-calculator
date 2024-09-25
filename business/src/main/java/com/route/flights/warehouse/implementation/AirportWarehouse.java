package com.route.flights.warehouse.implementation;

import com.route.flights.dto.AirportDto;
import com.route.flights.warehouse.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AirportWarehouse implements Warehouse<AirportDto, Long> {

    private final Map<Long, AirportDto> cache = new HashMap<>();

    @Override
    public Map<Long, AirportDto> getCached() {
        return cache;
    }
}

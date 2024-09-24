package com.route.flights.warehouse.implementation;

import com.route.flights.dto.AirportDto;
import com.route.flights.warehouse.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AirportWarehouse implements Warehouse<AirportDto, Long> {

    private final List<AirportDto> cachedList = new ArrayList<>();

    @Override
    public List<AirportDto> getCached() {
        return cachedList;
    }
}

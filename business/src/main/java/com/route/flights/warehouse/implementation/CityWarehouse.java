package com.route.flights.warehouse.implementation;

import com.route.flights.dto.CityDto;
import com.route.flights.warehouse.Warehouse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityWarehouse implements Warehouse<CityDto, Long> {

    private final List<CityDto> cachedList = new ArrayList<>();

    @Override
    public List<CityDto> getCached() {
        return cachedList;
    }
}

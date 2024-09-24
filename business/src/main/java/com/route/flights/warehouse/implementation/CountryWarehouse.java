package com.route.flights.warehouse.implementation;

import com.route.flights.dto.CountryDto;
import com.route.flights.warehouse.Warehouse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CountryWarehouse implements Warehouse<CountryDto, Long> {


    private final List<CountryDto> cachedList = new ArrayList<>();

    @Override
    public List<CountryDto> getCached() {
        return cachedList;
    }
}

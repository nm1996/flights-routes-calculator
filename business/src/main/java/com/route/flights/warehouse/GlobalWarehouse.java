package com.route.flights.warehouse;

import com.route.flights.warehouse.implementation.AirportWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
public class GlobalWarehouse {
    private final AirportWarehouse airportWarehouse;
    private final CityWarehouse cityWarehouse;
    private final CountryWarehouse countryWarehouse;
}

package com.route.flights.warehouse;

import com.route.flights.warehouse.implementation.AirportWarehouse;
import com.route.flights.warehouse.implementation.CityWarehouse;
import com.route.flights.warehouse.implementation.CountryWarehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {
                AirportWarehouse.class,
                CityWarehouse.class,
                CountryWarehouse.class,
                GlobalWarehouse.class
        }
)
class GlobalWarehouseTest {

    @Autowired
    GlobalWarehouse globalWarehouse;

    @Test
    void getAirportWarehouse() {
        assertNotNull(globalWarehouse.getAirportWarehouse());
        assertEquals(AirportWarehouse.class, globalWarehouse.getAirportWarehouse().getClass());
    }

    @Test
    void getCityWarehouse() {
        assertNotNull(globalWarehouse.getCityWarehouse());
        assertEquals(CityWarehouse.class, globalWarehouse.getCityWarehouse().getClass());
    }

    @Test
    void getCountryWarehouse() {
        assertNotNull(globalWarehouse.getCountryWarehouse());
        assertEquals(CountryWarehouse.class, globalWarehouse.getCountryWarehouse().getClass());
    }
}
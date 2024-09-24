package com.route.flights.service;

import com.route.flights.dto.AirportDto;

import java.util.List;

public interface AirportService {

    List<AirportDto> getAll();

    void deleteAirport(Long id);
}

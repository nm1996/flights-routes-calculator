package com.route.flights.rest;

import com.route.flights.dto.AirportDto;
import com.route.flights.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("")
    public List<AirportDto> getAllAirports(){
        return airportService.getAll();
    }
}

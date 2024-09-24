package com.route.flights.mapper;

import com.route.flights.dto.AirportDto;
import com.route.flights.entity.Airport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirportMapper implements Mapper<AirportDto, Airport> {

    private final CityMapper cityMapper;

    @Override
    public AirportDto mapToDto(Airport airport) {
        AirportDto dto = new AirportDto();

        mapLong(airport::getId, dto::setId);
        mapString(airport::getName, dto::setName);
        mapString(airport::getIata, dto::setIata);
        mapString(airport::getIcao, dto::setIcao);
        mapString(airport::getDst, dto::setDst);
        mapString(airport::getTz, dto::setTz);
        mapString(airport::getType, dto::setType);
        mapString(airport::getSource, dto::setSource);

        mapDouble(airport::getLatitude, dto::setLatitude);
        mapDouble(airport::getLongitude, dto::setLongitude);
        mapDouble(airport::getAltitude, dto::setAltitude);
        mapDouble(airport::getTimezone, dto::setTimezone);

        dto.setCity(cityMapper.mapToDto(airport.getCity()));

        return dto;
    }

    @Override
    public Airport mapToEntity(AirportDto airportDto) {
        Airport airport = new Airport();

        mapLong(airportDto::getId, airport::setId);
        mapString(airportDto::getName, airport::setName);
        mapString(airportDto::getIata, airport::setIata);
        mapString(airportDto::getIcao, airport::setIcao);
        mapString(airportDto::getDst, airport::setDst);
        mapString(airportDto::getTz, airport::setTz);
        mapString(airportDto::getType, airport::setType);
        mapString(airportDto::getSource, airport::setSource);

        mapDouble(airportDto::getLatitude, airport::setLatitude);
        mapDouble(airportDto::getLongitude, airport::setLongitude);
        mapDouble(airportDto::getAltitude, airport::setAltitude);
        mapDouble(airportDto::getTimezone, airport::setTimezone);

        airport.setCity(cityMapper.mapToEntity(airportDto.getCity()));

        return airport;
    }
}

package com.route.flights.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
public class AirportDto extends DTO {
    private String name;
    private CityDto city;
    private String iata;
    private String icao;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double timezone;
    private String dst;
    private String tz;
    private String type;
    private String source;

    public AirportDto() {
    }


    public AirportDto(Long id,
                      String name,
                      CityDto city,
                      String iata,
                      String icao,
                      double latitude,
                      double longitude,
                      double altitude,
                      double timezone,
                      String dst,
                      String tz,
                      String type,
                      String source) {
        super(id);
        this.dst = dst;
        this.name = name;
        this.city = city;
        this.iata = iata;
        this.icao = icao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.tz = tz;
        this.type = type;
        this.source = source;
    }
}

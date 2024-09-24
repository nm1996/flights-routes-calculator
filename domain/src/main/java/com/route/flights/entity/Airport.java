package com.route.flights.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airports")
public class Airport {


    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private City city;

    @Column(nullable = true)
    private String iata;

    @Column(nullable = true)
    private String icao;

    private Double latitude;
    private Double longitude;
    private Double altitude;

    private Double timezone;
    private String dst;
    private String tz;
    private String type;
    private String source;
}

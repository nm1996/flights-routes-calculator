package com.route.flights.dto;

import lombok.Data;

@Data
public abstract class DTO {
    private Long id;

    public DTO(){}

    public DTO(Long id){
        this.id = id;
    }
}

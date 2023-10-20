package com.example.athenticationdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirPortObjectDto {
    private String name;
    private Double  latitude;
    private Double  longitude;
    private Double  distance;
}

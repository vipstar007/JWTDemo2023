package com.example.athenticationdemo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopFiveNearestAirportsRequestDto {
    @Null(message = "latitude isn't null")
    @DecimalMin(value = "0", message ="latitude must be greater than 0")
    private Double latitude;
    @Null(message = "longitude isn't null")
    @DecimalMin(value = "0", message ="longitude must be greater than 0")
    private Double longitude;
}

package com.example.athenticationdemo.airport;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport_codes")
@IdClass(AirportKey.class)
public class AirportCodes {

@Id
private String ident;
    private String type;
    private String name;
    private String elevation_ft;
    private String continent;
    private String iso_country;
    private String iso_region;
    private String municipality;
    private String gps_code;
    private String iata_code;
    private String local_code;
    private String coordinates;
}

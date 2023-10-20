package com.example.athenticationdemo.airport;


import com.example.athenticationdemo.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface AirportCodesRepository extends JpaRepository<AirportCodes,String> {
    @Query(value = """
      select a.name from AirportCodes a 
      where a.iso_country = :code\s
      """)
    List<String> findAirportNameByCountryCode(String code);
    @Query(value = "SELECT name,CAST(split_part(coordinates, ', ', 1) AS double precision) AS latitude,CAST(split_part(coordinates, ', ', 2) AS double precision) AS longitude FROM airport_codes", nativeQuery = true)
    List<Object[]> findAirportsWithLatitudeAndLongitude();
}

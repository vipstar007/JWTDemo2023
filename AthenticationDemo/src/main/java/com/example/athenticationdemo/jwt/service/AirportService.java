package com.example.athenticationdemo.jwt.service;

import com.example.athenticationdemo.airport.AirportCodesRepository;
import com.example.athenticationdemo.dto.AirPortObjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportCodesRepository airportCodesRepository;
public List<AirPortObjectDto>getAirportNameByContryCode(String code)throws Exception{
    String newcode = code.trim().toUpperCase();
    List<String> listName = airportCodesRepository.findAirportNameByCountryCode(newcode);
    List<AirPortObjectDto> Airports = new ArrayList<>();
    for (String i: listName
         ) {
        AirPortObjectDto apo = new AirPortObjectDto();
        apo.setName(i);
        Airports.add(apo);
    }
    return Airports;
}
public List<AirPortObjectDto>getTopFiveNearestAirPort(double targetLatitude, double targetLongitude){
    //get all
    List<Object[]> listAirPort = airportCodesRepository.findAirportsWithLatitudeAndLongitude();
    List<AirPortObjectDto> nearestAirports = new ArrayList<>();
    for (Object[] t :
            listAirPort) {
        String name = (String) t[0];
        double latitude = (Double) t[1];
        double longitude = (Double) t[2];
        double distance = calculateDistance(targetLatitude, targetLongitude, latitude, longitude);
        nearestAirports.add(new AirPortObjectDto(name, latitude, longitude, distance));
    }
    // Sắp xếp danh sách theo khoảng cách
    Collections.sort(nearestAirports, Comparator.comparingDouble(AirPortObjectDto::getDistance));

    nearestAirports = nearestAirports.subList(0, 5);
    return nearestAirports;
}
    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Bán kính trái đất (đơn vị: km)

        // Chuyển đổi tọa độ Latitude và Longitude từ độ sang radian
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Tính độ delta của tọa độ Latitude và Longitude
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Sử dụng công thức Haversine để tính khoảng cách
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Khoảng cách (đơn vị: km)

        return distance;
    }
}

package com.example.athenticationdemo.controller;

import com.example.athenticationdemo.common.Common;
import com.example.athenticationdemo.dto.AirPortObjectDto;
import com.example.athenticationdemo.dto.RegisterRequestDto;
import com.example.athenticationdemo.dto.TopFiveNearestAirportsRequestDto;
import com.example.athenticationdemo.jwt.service.AirportService;
import com.example.athenticationdemo.response.GenericResponse;
import com.example.athenticationdemo.response.GenericResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/airport")
@RequiredArgsConstructor
public class AirportController {
    @Autowired
    AirportService airportService;
    @Operation(summary = "Get Airport By CountryCode", description = "Get Airport By CountryCode")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",

            array = @ArraySchema(schema = @Schema(implementation = AirPortObjectDto.class))) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GenericResponseError.class))) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GenericResponseError.class))) }) })
   @GetMapping("getAirportNameByCountryCode/{code}")
    public ResponseEntity<Object> getAirportNameByContryCode(@PathVariable("code")String code){
       try {
           if("".equals(code))
               return Common.returnErrorFormat(HttpStatus.BAD_REQUEST, "ListError", "Code is null");
           List<AirPortObjectDto> listAir = airportService.getAirportNameByContryCode(code);
           if(listAir== null || listAir.isEmpty())
               return Common.returnErrorFormat(HttpStatus.BAD_REQUEST, "ListError", "Country haven't Airport");

           GenericResponse<List<AirPortObjectDto>> response = new GenericResponse<List<AirPortObjectDto>>(HttpStatus.OK,
                   "Get data successfully", listAir);
           return ResponseEntity.ok().body(response);
       }catch (Exception e) {
           return Common.returnErrorFormat(HttpStatus.BAD_REQUEST, "ListError", e.getMessage());
       }


   }
    @Operation(summary = "Get Top Five Airport nearest", description = "Get Top Five Airport nearest")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",

            array = @ArraySchema(schema = @Schema(implementation = AirPortObjectDto.class))) }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GenericResponseError.class))) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GenericResponseError.class))) }) })
   @PostMapping("getTopFiveNearestAirports")
    public ResponseEntity<Object>getTopFiveNearestAirports( @RequestBody TopFiveNearestAirportsRequestDto request){
       try {
           List<AirPortObjectDto> listAir = airportService.getTopFiveNearestAirPort(request.getLatitude(),request.getLongitude());
           GenericResponse<List<AirPortObjectDto>> response = new GenericResponse<List<AirPortObjectDto>>(HttpStatus.OK,
                   "Get five Airport nearest successfully", listAir);
           return ResponseEntity.ok().body(response);
       }catch (Exception e) {
        return Common.returnErrorFormat(HttpStatus.BAD_REQUEST, "ListError", e.getMessage());
    }
   }
}

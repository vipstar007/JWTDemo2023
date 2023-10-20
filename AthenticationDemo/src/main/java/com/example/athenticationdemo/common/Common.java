package com.example.athenticationdemo.common;

import com.example.athenticationdemo.response.GenericResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Common {
    public static ResponseEntity<Object> returnErrorFormat(HttpStatus statusCode, String errorName,
                                                           String errorRepost) {
        GenericResponseError apiError = new GenericResponseError(statusCode, errorName, errorRepost, 1);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

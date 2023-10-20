package com.example.athenticationdemo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class GenericResponseError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private int numberOfError;
    private List<String> errors;

    public GenericResponseError(HttpStatus status, String message, List<String> errors,int numberOfError) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.numberOfError = numberOfError;
        this.timestamp = LocalDateTime.now();
    }

    public GenericResponseError(HttpStatus status, String message, String error, int numberOfError) {
        super();
        this.status = status;
        this.message = message;
        this.numberOfError = numberOfError;
        errors = Arrays.asList(error);
        this.timestamp = LocalDateTime.now();
    }

    public GenericResponseError() {
        super();
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getNumberOfError() {
        return numberOfError;
    }

    public void setNumberOfError(int numberOfError) {
        this.numberOfError = numberOfError;
    }
}

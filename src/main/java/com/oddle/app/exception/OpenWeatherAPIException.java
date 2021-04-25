package com.oddle.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class OpenWeatherAPIException extends HttpClientErrorException {
    public OpenWeatherAPIException(HttpStatus statusCode) {
        super(statusCode);
    }
}

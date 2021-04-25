package com.oddle.app.config;

import com.oddle.app.exception.OpenWeatherAPIException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handle exceptions
 * @author squillopas
 */
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler({ OpenWeatherAPIException.class })
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(final OpenWeatherAPIException ex) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setException(ex.getClass().getSimpleName());
        if(ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            errorResponse.setMessage("Invalid API key");
        } else if(ex.getStatusCode().equals(HttpStatus.NOT_FOUND) || ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            errorResponse.setMessage("No data found");
        }
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String exception;
        private String message;
    }
}

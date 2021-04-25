package com.oddle.app.controller;

import com.oddle.app.exception.OpenWeatherAPIException;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.model.openweather.OWAPIResponse;
import com.oddle.app.model.openweather.OWCityData;
import com.oddle.app.service.WeatherLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller for weather entity
 * @author squillopas
 */
@RestController
@RequestMapping(value = "/api/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WeatherRestController {

    @Autowired
    private WeatherLogService weatherLogService;

    @PostMapping(value="/search")
    public ResponseEntity<List<WeatherLogEntity>> retrieveAndSaveWeather(@ModelAttribute("searchString") String searchString){
        List<WeatherLogEntity> result = new ArrayList<>();

    	if(searchString!=null) {
    	    OWAPIResponse owapiResponse = weatherLogService.searchFromOWAPI(searchString);
    	    if(!owapiResponse.getList().isEmpty()) {
                for (OWCityData owCityData : owapiResponse.getList()) {
                    result.add(weatherLogService.create(owCityData));
                }
            }

            if(result.isEmpty()) throw new OpenWeatherAPIException(HttpStatus.NOT_FOUND);
    	}

    	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteWeatherLog(@PathVariable final Long id) {
        boolean isRemoved = weatherLogService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

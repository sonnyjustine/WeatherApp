package com.oddle.app.service;

import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.model.openweather.OWAPIResponse;
import com.oddle.app.model.openweather.OWCityData;

import java.util.List;

/**
 * Service interface used for managing weather logs
 * @author squillopas
 */
public interface WeatherLogService {

    /**
     * Search data from Open Weather API
     * @return OWAPIResponse object
     */
    OWAPIResponse searchFromOWAPI(String searchString);
    /**
     * Create WeatherLogEntity from data retrieved from OpenWeather API (OpenWeatherData)
     * @param openWeatherData data from OpenWeather API
     * @return
     */
    WeatherLogEntity create(final OWCityData openWeatherData);

    /**
     * List all weather logs
     * @return list of all weather logs
     */
    List<WeatherLogEntity> findAll();

    /**
     * List all weather logs passing the search criteria
     * @param weatherLogEntity weather log entity that will serve as example for the query
     * @return list of weather logs
     */
    List<WeatherLogEntity> findByExample(WeatherLogEntity weatherLogEntity);

    /**
     * Delete weather log
     * @param id weather log id
     * @return false if id does not exist
     */
    boolean delete(final Long id);
}

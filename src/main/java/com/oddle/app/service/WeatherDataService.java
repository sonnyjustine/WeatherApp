package com.oddle.app.service;

import com.oddle.app.entity.WeatherDataEntity;

import java.util.List;

/**
 * Service interface used for managing weather data
 * @author squillopas
 */
public interface WeatherDataService {
    /**
     * List all weather data
     * @return list of all weather data
     */
    List<WeatherDataEntity> findAll();
}

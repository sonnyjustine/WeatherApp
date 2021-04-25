package com.oddle.app.factory;

import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.model.openweather.OWCityData;

public interface WeatherLogFactory {
    /**
     * Create new WeatherLogEntity based on the data retrieved from OpenWeather API
     * @param openWeatherData
     * @return
     */
    WeatherLogEntity newWeatherLogEntity(OWCityData openWeatherData);
}

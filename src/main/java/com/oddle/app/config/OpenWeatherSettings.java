package com.oddle.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OpenWeather API properties
 * @author squillopas
 */
@ConfigurationProperties(prefix = "openweather")
public class OpenWeatherSettings {
    private String apiUrl;
    private String apiKey;
    private String imageUrl;
    private String unit;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
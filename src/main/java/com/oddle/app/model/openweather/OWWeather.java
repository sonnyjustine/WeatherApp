package com.oddle.app.model.openweather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OWWeather {
    private long id;
    private String main;
    private String description;
    private String icon;
}

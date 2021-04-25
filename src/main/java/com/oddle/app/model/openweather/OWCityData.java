package com.oddle.app.model.openweather;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OWCityData {
    private long id;
    private String name;
    private long timezone;
    private long dt;
    private int visibility;

    private OWMain main;
    private OWCloud clouds;
    private OWCoord coord;
    private OWSys sys;
    private List<OWWeather> weather;
    private OWWind wind;
}

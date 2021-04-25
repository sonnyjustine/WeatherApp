package com.oddle.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class TemperatureEntity {
    private Double temperature;

    private Double feelsLikeTemp;

    private Double maxTemp;

    private Double minTemp;

    private Double pressure;

    private Integer humidity;
}

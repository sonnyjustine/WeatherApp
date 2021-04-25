package com.oddle.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class for persisting data for a weather type
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
@Table(name = "weather_data")
public class WeatherDataEntity implements Serializable {
    private static final long serialVersionUID = 7574496319859139041L;

    /**
     * ID value is based on the weather ID in OpenWeather API
     */
    @Id
    @Column(name="weather_data_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String weatherGroup;

    @Column(columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(columnDefinition = "VARCHAR(10)")
    private String icon;
}

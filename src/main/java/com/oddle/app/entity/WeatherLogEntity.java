package com.oddle.app.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity class for persisting weather logs
 * @author squillopas
 */
@Entity
@Getter @Setter @EqualsAndHashCode @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather_log", indexes = @Index(name="idx_weather_log_city_name", columnList = "cityName"))
public class WeatherLogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1701640189358923180L;

    private Long cityId;

    @Column(columnDefinition = "VARCHAR(100)")
    private String cityName;

    private LocalDateTime weatherDate;

    private Integer visibility;

    private Integer cloudiness;

    private Double windSpeed;

    private Integer windDegree;

    @Column(columnDefinition = "CHAR(2)")
    private String countryCode;

    private Double longitude;

    private Double latitude;

    @Embedded
    private TemperatureEntity temperature;

    @ManyToOne
    @JoinColumn(name="weather_data_id", nullable=false)
    private WeatherDataEntity weatherData;

    public String getWeatherDateStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        return this.weatherDate == null ? null : this.weatherDate.format(formatter) + " UTC";
    }
}

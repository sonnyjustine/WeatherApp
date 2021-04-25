package com.oddle.app.factory;

import com.oddle.app.entity.TemperatureEntity;
import com.oddle.app.entity.WeatherDataEntity;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.model.openweather.OWMain;
import com.oddle.app.model.openweather.OWCityData;
import com.oddle.app.model.openweather.OWWeather;
import com.oddle.app.repository.WeatherDataRepository;
import com.oddle.app.repository.WeatherLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class WeatherLogFactoryImpl implements WeatherLogFactory {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    @Override
    public WeatherLogEntity newWeatherLogEntity(OWCityData owCityData) {
        LocalDateTime weatherDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(owCityData.getDt() * 1000), ZoneOffset.UTC);
        Optional<WeatherLogEntity> optional = this.findByCityIDAndWeatherDate(owCityData.getId(), weatherDate);

        if(optional.isPresent()) {
            return optional.get();
        }

        // Build new Weather Log Entity
        TemperatureEntity temp = new TemperatureEntity();
        OWMain main = owCityData.getMain();
        temp.setTemperature(main.getTemp());
        temp.setFeelsLikeTemp(main.getFeelsLike());
        temp.setHumidity(main.getHumidity());
        temp.setMinTemp(main.getTempMin());
        temp.setMaxTemp(main.getTempMax());
        temp.setPressure(main.getPressure());

        OWWeather weather = owCityData.getWeather().get(0);
        WeatherDataEntity wde = new WeatherDataEntity();
        wde.setId(weather.getId());
        wde.setDescription(weather.getDescription());
        wde.setWeatherGroup(weather.getMain());
        wde.setIcon(weather.getIcon());
        weatherDataRepository.save(wde);

        WeatherLogEntity wle = WeatherLogEntity.builder()
                .weatherDate(weatherDate)
                .cityId(owCityData.getId())
                .cityName(owCityData.getName())
                .visibility(owCityData.getVisibility())
                .cloudiness(owCityData.getClouds().getAll())
                .windSpeed(owCityData.getWind().getSpeed())
                .windDegree(owCityData.getWind().getDeg())
                .countryCode(owCityData.getSys().getCountry())
                .longitude(owCityData.getCoord().getLon())
                .latitude(owCityData.getCoord().getLat())
                .temperature(temp)
                .weatherData(wde)
                .build();

        return wle;
    }

    /**
     * Check if weather data already exists in the DB
     * We are using the date and cityId value since there's no need to save new data in DB for same data with same date and time
     * @param weatherDate
     * @return
     */
    private Optional<WeatherLogEntity> findByCityIDAndWeatherDate(long cityId, LocalDateTime weatherDate) {
        WeatherLogEntity wle = WeatherLogEntity.builder()
                .cityId(cityId)
                .weatherDate(weatherDate)
                .build();

        return weatherLogRepository.findOne(Example.of(wle));
    }
}

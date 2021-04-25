package com.oddle.app.service;

import com.oddle.app.entity.WeatherDataEntity;
import com.oddle.app.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Override
    public List<WeatherDataEntity> findAll() {
        return weatherDataRepository.findAllByOrderByWeatherGroupDesc();
    }
}

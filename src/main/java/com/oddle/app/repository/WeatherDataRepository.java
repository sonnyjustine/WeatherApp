package com.oddle.app.repository;

import com.oddle.app.entity.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {
    List<WeatherDataEntity> findAllByOrderByWeatherGroupDesc();
}

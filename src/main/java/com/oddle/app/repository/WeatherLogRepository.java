package com.oddle.app.repository;

import com.oddle.app.entity.WeatherLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherLogRepository extends JpaRepository<WeatherLogEntity, Long>, QueryByExampleExecutor<WeatherLogEntity> {
}

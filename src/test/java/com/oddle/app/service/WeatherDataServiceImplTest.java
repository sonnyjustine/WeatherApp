package com.oddle.app.service;

import com.oddle.app.entity.WeatherDataEntity;
import com.oddle.app.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherDataServiceImplTest {

    @Autowired
    private WeatherDataService weatherDataService;

    @MockBean
    private WeatherDataRepository weatherDataRepository;

    private WeatherDataEntity entity1, entity2;

    @BeforeEach
    public void setUp() {
        entity1 = new WeatherDataEntity();
        entity1.setId(1l);
        entity1.setWeatherGroup("Cloudy");
        entity1.setDescription("With a chance of meatballs");
        entity1.setIcon("test1");

        entity2 = new WeatherDataEntity();
        entity2.setId(2l);
        entity2.setWeatherGroup("Cloudy");
        entity2.setDescription("With a chance of meatballs 2");
        entity2.setIcon("test2");
    }

    @Test
    void findAll() {
        List<WeatherDataEntity> all = Arrays.asList(entity1, entity2);

        when(weatherDataRepository.findAllByOrderByWeatherGroupDesc())
                .thenReturn(all);

        List<WeatherDataEntity> result = weatherDataService.findAll();

        assertEquals(all.size(), result.size());
        assertEquals(all.get(0), result.get(0));
        assertEquals(all.get(1), result.get(1));
    }
}
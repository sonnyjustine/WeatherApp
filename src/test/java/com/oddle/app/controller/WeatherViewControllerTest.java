package com.oddle.app.controller;

import com.oddle.app.entity.WeatherDataEntity;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.service.WeatherDataService;
import com.oddle.app.service.WeatherLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherViewController.class)
public class WeatherViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherLogService weatherLogService;

    @MockBean
    private WeatherDataService weatherDataService;

    private WeatherLogEntity entity1, entity2;

    private WeatherDataEntity wdEntity1, wdEntity2;

    @BeforeEach
    void setUp() {
        // Weather Data entities
        wdEntity1 = new WeatherDataEntity();
        wdEntity1.setId(1l);
        wdEntity1.setWeatherGroup("Cloudy");
        wdEntity1.setDescription("With a chance of meatballs");
        wdEntity1.setIcon("test1");

        wdEntity2 = new WeatherDataEntity();
        wdEntity2.setId(2l);
        wdEntity2.setWeatherGroup("Clear");
        wdEntity2.setDescription("Very clear");
        wdEntity2.setIcon("test2");

        // WeatherLogEntities
        entity1 = WeatherLogEntity.builder()
                .cityName("Singapore")
                .cityId(1l)
                .weatherData(wdEntity1)
                .build();

        entity2 = WeatherLogEntity.builder()
                .cityName("Manila")
                .cityId(2l)
                .weatherData(wdEntity2)
                .build();
    }

    @Test
    void index_shouldReturnHttpStatusOK() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather"));
    }

    @Test
    void weatherList_noSearchFilter_shouldReturnHttpStatusOK() throws Exception {
        WeatherLogEntity example = new WeatherLogEntity();
        when(weatherLogService.findByExample(example)).thenReturn(Arrays.asList(entity1, entity2));
        when(weatherDataService.findAll()).thenReturn(Arrays.asList(wdEntity1, wdEntity2));

        this.mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather-list"))
                .andExpect(model().attribute("weatherLogsList", Arrays.asList(entity1, entity2)))
                .andExpect(model().attribute("weatherDataList", Arrays.asList(wdEntity1, wdEntity2)));
    }

    @Test
    void weatherList_withSearchFilter_shouldReturnHttpStatusOK() throws Exception {
        WeatherLogEntity example = WeatherLogEntity.builder().cityName("Singapore").build();
        when(weatherLogService.findByExample(example)).thenReturn(Arrays.asList(entity1));
        when(weatherDataService.findAll()).thenReturn(Arrays.asList(wdEntity1, wdEntity2));

        this.mockMvc.perform(get("/list?cityName=Singapore"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather-list"))
                .andExpect(model().attribute("weatherLogsList", Arrays.asList(entity1)))
                .andExpect(model().attribute("weatherDataList", Arrays.asList(wdEntity1, wdEntity2)));
    }

    @Test
    void weatherList_noData_shouldReturnHttpStatusOK() throws Exception {
        this.mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("weather-list"))
                .andExpect(model().attribute("weatherLogsList", new ArrayList<>()))
                .andExpect(model().attribute("weatherDataList", new ArrayList<>()));
    }

    @Test
    void incorrectUrl_shouldReturnHttpStatusNotFound() throws Exception {
        this.mockMvc.perform(get("/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}
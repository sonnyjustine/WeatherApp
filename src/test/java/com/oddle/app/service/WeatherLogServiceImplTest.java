package com.oddle.app.service;

import com.oddle.app.config.OpenWeatherSettings;
import com.oddle.app.entity.WeatherDataEntity;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.exception.OpenWeatherAPIException;
import com.oddle.app.factory.WeatherLogFactory;
import com.oddle.app.model.openweather.OWAPIResponse;
import com.oddle.app.model.openweather.OWCityData;
import com.oddle.app.model.openweather.OWWeather;
import com.oddle.app.repository.WeatherLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherLogServiceImplTest {

    @Autowired
    private WeatherLogService weatherLogService;

    @Autowired
    private OpenWeatherSettings openWeatherSettings;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WeatherLogRepository weatherLogRepository;

    @MockBean
    private WeatherLogFactory weatherLogFactory;

    private OWAPIResponse owapiResponse, emptyOwApiResponse;

    private WeatherLogEntity weatherLogEntity;

    @BeforeEach
    void setUp() {
        // Empty response from OpenWeather API
        emptyOwApiResponse = new OWAPIResponse();
        emptyOwApiResponse.setList(new ArrayList<>());

        // Response from OpenWeather API
        owapiResponse = new OWAPIResponse();
        OWCityData owCityData = new OWCityData();
        owCityData.setName("Manila");
        owCityData.setId(9l);
        OWWeather owWeather = new OWWeather();
        owWeather.setMain("Clouds");
        owWeather.setDescription("Very cloudy");
        owCityData.setWeather(Arrays.asList(owWeather));
        owapiResponse.setList(Arrays.asList(owCityData));

        // WeatherLogEntity based on OW response
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();
        weatherDataEntity.setWeatherGroup("Clouds");
        weatherDataEntity.setDescription("Very cloudy");
        weatherLogEntity = WeatherLogEntity.builder()
                .cityName("Manila")
                .cityId(9l)
                .weatherData(weatherDataEntity)
                .build();
    }

    @Test
    void searchFromOWAPI_shouldReturnOWAPIResponse() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap()))
                .thenReturn(new ResponseEntity<>(owapiResponse, HttpStatus.OK));

        OWAPIResponse result = weatherLogService.searchFromOWAPI("Manila");
        assertEquals(result, owapiResponse);
    }

    @Test
    void searchFromOWAPI_shouldThrowOpenWeatherAPIException_whenUnauthorized() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap()))
                .thenThrow(HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, null, null, null, null));

        OpenWeatherAPIException exception = assertThrows(OpenWeatherAPIException.class, () -> {
            weatherLogService.searchFromOWAPI("Manila");
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void searchFromOWAPI_shouldThrowOpenWeatherAPIException_whenBadRequest() {
        when(restTemplate.getForEntity(any(String.class), any(Class.class), anyMap()))
                .thenThrow(HttpClientErrorException.Unauthorized.create(HttpStatus.BAD_REQUEST, null, null, null, null));

        OpenWeatherAPIException exception = assertThrows(OpenWeatherAPIException.class, () -> {
            weatherLogService.searchFromOWAPI("Manila");
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void create_shouldReturnWeatherLogEntity() {
        when(weatherLogFactory.newWeatherLogEntity(any()))
                .thenReturn(weatherLogEntity);

        WeatherLogEntity savedEntity = weatherLogEntity;
        weatherLogEntity.setId(100l);
        when(weatherLogRepository.save(any()))
                .thenReturn(savedEntity);

        WeatherLogEntity getSavedEntity = weatherLogService.create(owapiResponse.getList().get(0));
        assertEquals(savedEntity, getSavedEntity);
    }

    @Test
    void findAll_shouldReturnAllEntitiesRetrievedByRepo() {
        WeatherLogEntity entity1 = weatherLogEntity;
        entity1.setId(1l);
        WeatherLogEntity entity2 = weatherLogEntity;
        entity2.setId(2l);

        List<WeatherLogEntity> all = Arrays.asList(entity1, entity2);
        when(weatherLogRepository.findAll(any(Sort.class)))
                .thenReturn(all);

        List<WeatherLogEntity> result = weatherLogService.findAll();
        assertEquals(all.size(), result.size());
        assertEquals(all.get(0), result.get(0));
        assertEquals(all.get(1), result.get(1));
    }

    @Test
    void findByExample_shouldReturnAllEntitiesRetrievedByRepo() {
        WeatherLogEntity entity1 = weatherLogEntity;
        entity1.setId(1l);
        WeatherLogEntity entity2 = weatherLogEntity;
        entity2.setId(2l);

        List<WeatherLogEntity> all = Arrays.asList(entity1, entity2);
        when(weatherLogRepository.findAll(any(), any(Sort.class)))
                .thenReturn(all);

        List<WeatherLogEntity> result = weatherLogService.findByExample(weatherLogEntity);
        assertEquals(all.size(), result.size());
        assertEquals(all.get(0), result.get(0));
        assertEquals(all.get(1), result.get(1));
    }

    @Test
    void delete_shouldReturnTrueIfExists() {
        when(weatherLogRepository.existsById(1l)).thenReturn(true);

        assertEquals(true, weatherLogService.delete(1l));
    }

    @Test
    void delete_shouldReturnFalseIfDoesNotExist() {
        when(weatherLogRepository.existsById(1l)).thenReturn(false);

        assertEquals(false, weatherLogService.delete(1l));
    }
}
package com.oddle.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.model.openweather.OWAPIResponse;
import com.oddle.app.model.openweather.OWCityData;
import com.oddle.app.service.WeatherLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherRestController.class)
public class WeatherRestControllerTest {
    private static String BASE_PATH = "http://localhost/api/weather";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WeatherLogService weatherLogService;

    private OWAPIResponse owapiResponse, emptyOwApiResponse;

    private WeatherLogEntity weatherLogEntity;

    @BeforeEach
    public void setUp() {
        // Empty response from OpenWeather API
        emptyOwApiResponse = new OWAPIResponse();
        emptyOwApiResponse.setList(new ArrayList<>());

        // Response from OpenWeather API
        owapiResponse = new OWAPIResponse();
        OWCityData owCityData = new OWCityData();
        owCityData.setName("Manila");
        owCityData.setId(9l);
        owapiResponse.setList(Arrays.asList(owCityData));

        // WeatherLogEntity based on OW response
        weatherLogEntity = WeatherLogEntity.builder()
                .cityName("Manila")
                .cityId(9l)
                .build();
    }

    @Test
    void retrieveAndSaveWeather_shouldReturnCreatedWeatherLogEntity() throws Exception {
        String searchString = "Manila";
        when(weatherLogService.searchFromOWAPI(searchString)).thenReturn(owapiResponse);
        when(weatherLogService.create(owapiResponse.getList().get(0))).thenReturn(weatherLogEntity);

        mockMvc.perform(post(BASE_PATH + "/search?searchString=" + searchString)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(weatherLogEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].cityName", is(searchString)))
                .andExpect(jsonPath("$.[0].cityId", is(9)));
    }

    @Test
    void retrieveAndSaveWeather_shouldThrowOpenWeatherAPIException() throws Exception {
        String searchString = "Imaginary City";
        when(weatherLogService.searchFromOWAPI(searchString)).thenReturn(emptyOwApiResponse);

        mockMvc.perform(post(BASE_PATH + "/search?searchString=" + searchString)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(weatherLogEntity)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception", is("OpenWeatherAPIException")));
    }

    @Test
    void deleteJob_shouldDeleteWeatherLog() throws Exception {
        long idToDelete = 9l;
        when(weatherLogService.delete(idToDelete)).thenReturn(true);

        mockMvc.perform(delete(BASE_PATH + "/delete/" + idToDelete)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(idToDelete)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteJob_shouldReturnNotFound() throws Exception {
        long idToDelete = 9l;
        when(weatherLogService.delete(idToDelete)).thenReturn(false);

        mockMvc.perform(delete(BASE_PATH + "/delete/" + idToDelete)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(idToDelete)))
                .andExpect(status().isNotFound());
    }
}
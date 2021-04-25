package com.oddle.app.service;

import com.oddle.app.exception.OpenWeatherAPIException;
import com.oddle.app.config.OpenWeatherSettings;
import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.factory.WeatherLogFactory;
import com.oddle.app.model.openweather.OWAPIResponse;
import com.oddle.app.model.openweather.OWCityData;
import com.oddle.app.repository.WeatherLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherLogServiceImpl implements WeatherLogService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OpenWeatherSettings openWeatherSettings;

    @Autowired
    private WeatherLogRepository weatherLogRepository;

    @Autowired
    private WeatherLogFactory weatherLogFactory;

    @Override
    public OWAPIResponse searchFromOWAPI(String searchString) {
        String URL = openWeatherSettings.getApiUrl() + "?q={q}&appId={appId}&units={units}";
        Map<String,String> params = new HashMap<>();
        params.put("q", searchString);
        params.put("appId", openWeatherSettings.getApiKey());
        params.put("units", openWeatherSettings.getUnit());

        ResponseEntity<OWAPIResponse> response;

        try {
            response = restTemplate.getForEntity(URL, OWAPIResponse.class, params);
        } catch(HttpClientErrorException e) {
            throw new OpenWeatherAPIException(e.getStatusCode());
        }

        return response.getBody();
    }

    @Override
    public WeatherLogEntity create(OWCityData openWeatherData) {
        WeatherLogEntity wle = weatherLogFactory.newWeatherLogEntity(openWeatherData);
        return weatherLogRepository.save(wle);
    }

    @Override
    public List<WeatherLogEntity> findAll() {
        return weatherLogRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public List<WeatherLogEntity> findByExample(WeatherLogEntity weatherLogEntity) {
        return weatherLogRepository.findAll(Example.of(weatherLogEntity), Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public boolean delete(Long id) {
        if(!weatherLogRepository.existsById(id)) {
            return false;
        }
        weatherLogRepository.deleteById(id);
        return true;
    }
}

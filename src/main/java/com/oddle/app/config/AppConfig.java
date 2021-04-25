package com.oddle.app.config;

import com.oddle.app.factory.WeatherLogFactory;
import com.oddle.app.factory.WeatherLogFactoryImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({OpenWeatherSettings.class})
public class AppConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public WeatherLogFactory weatherLogFactory() {
		return new WeatherLogFactoryImpl();
	}
}

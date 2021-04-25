package com.oddle.app.controller;

import com.oddle.app.entity.WeatherLogEntity;
import com.oddle.app.service.WeatherDataService;
import com.oddle.app.service.WeatherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WeatherViewController {

	@Autowired
	private WeatherLogService weatherLogService;

	@Autowired
	private WeatherDataService weatherDataService;

	@GetMapping(value="")
	public ModelAndView index() {
		return new ModelAndView("weather");
	}

	@GetMapping(value="list")
	public ModelAndView weatherList(@ModelAttribute WeatherLogEntity weatherLogEntity) {
		ModelAndView modelAndView = new ModelAndView("weather-list");
		modelAndView.addObject("weatherLogsList", weatherLogService.findByExample(weatherLogEntity));
		modelAndView.addObject("weatherDataList", weatherDataService.findAll());
		return modelAndView;
	}

	@InitBinder
	public void initBinder( WebDataBinder binder )
	{
		// Set empty values as null instead of empty string.
		binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
	}
}

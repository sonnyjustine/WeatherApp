package com.oddle.app.model.openweather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OWAPIResponse {
    private List<OWCityData> list;
}

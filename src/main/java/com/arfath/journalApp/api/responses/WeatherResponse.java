package com.arfath.journalApp.api.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse{


    public Current current;

    @Getter
    @Setter
    public class Current{

        @JsonProperty("weather_description") //this is to tell jackson that its named in snake case
        public List<String> weatherDescription; //snake_case to camelCase naming convention

        public int temperature;

        public int feelslike;

    }
}



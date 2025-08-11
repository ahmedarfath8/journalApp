package com.arfath.journalApp.service;

import com.arfath.journalApp.api.responses.WeatherResponse;
import com.arfath.journalApp.cache.AppCache;
import com.arfath.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${api.key}")
    private String apikey;

    @Autowired
    private AppCache appCache;


    //private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY"; this is of no use now cuz we are using now AppCache instead load once use many times

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String City) {
        //before we call api we check if the data is there in redis by using get method
        WeatherResponse weatherResponse = redisService.get("weather_of_" + City, WeatherResponse.class);//just appended string for better readability
        if(weatherResponse != null){
            return weatherResponse;
        }else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apikey).replace(Placeholders.CITY, City);//since were now using constants  can change this from .replace("<city>", City) to replace(Placeholders.CITY, City)//appCache.appCache.get("WEATHER_API") instead of hardcoding here what we do is use enum keys like this appCache.appCache.get(AppCache.keys.WEATHER_API.toString()
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody(); //here once we get the data from weather api we will save it in redis
            redisService.set("weather_of_" + City,body,300L);
            return body;
        }

        //this is simple example how to send post request to api
        //String finalApi = API.replace("API_KEY", apikey).replace("CITY", City);
        //ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, here request entity , WeatherResponse.class);
        //WeatherResponse body = response.getBody();
        //return body;
    }
}

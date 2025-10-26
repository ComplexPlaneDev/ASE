package com.adapter;

public interface WeatherApiProcessor {
    String getCurrentTemperature(String city);

    String getWeatherCondition(String city);
    
    int getHumidityLevel(String city);
}

package com.adapter.adapters;

import com.adapter.WeatherApiProcessor;

public class WeatherAPI implements WeatherApiProcessor {
  private final com.adapter.adaptee.WeatherAPI weatherApiProvider;

  public WeatherAPI (com.adapter.adaptee.WeatherAPI weatherApiProvider) {
    this.weatherApiProvider = weatherApiProvider;
  };

  @Override
  public int getHumidityLevel(String location) {
    return weatherApiProvider.getHumidity(location);
  }

  @Override
  public String getCurrentTemperature(String location) {
    return weatherApiProvider.fetchTemp(location);
  }

  @Override
  public String getWeatherCondition(String location) {
    return weatherApiProvider.retrieveConditions(location);
  }
}
package com.adapter;

public class Main {
    public static void main(String[] args) {
        com.adapter.adaptee.WeatherAPI thirdPartyWeatherAdaptee = new com.adapter.adaptee.WeatherAPI();
        com.adapter.adapters.WeatherAPI thirdPartyWeatherAdapter = new com.adapter.adapters.WeatherAPI(thirdPartyWeatherAdaptee);

        System.out.println(thirdPartyWeatherAdapter.getCurrentTemperature("location"));

    }
}
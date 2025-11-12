package com.adapter.adaptee;

public class WeatherAPI {
    public String fetchTemp(String location) {
        return "The temperature is 21 degrees";
    }

    public String retrieveConditions(String location) {
        return "The Conditons are cloudy";
    }

    public int getHumidity(String location) {
        return 52;
    }
}
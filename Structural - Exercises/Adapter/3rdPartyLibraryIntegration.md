# Third-Party Library Integration

## Problem Description

Your weather application has been successfully using an internal weather service for months. However, due to cost considerations, management has decided to switch to a new third-party weather API provider that offers better pricing.

The problem is that the new weather service has completely different method names and data structures compared to what your application currently expects. Your application code is used in multiple places throughout the system, and rewriting all the client code would be risky and time-consuming.

## Current Application Interface

Your application currently expects to work with any weather service that implements these methods:

```java
public abstract String getCurrentTemperature(String city);
public abstract String getWeatherCondition(String city);
public abstract int getHumidityLevel(String city);
```

## New Third-Party Weather Service

The new weather service provider offers these methods:

```java
// Third-party weather service methods (cannot be modified)
public String fetchTemp(String location);
public String retrieveConditions(String location);
public int getHumidity(String location);
```

## Your Task

You need to integrate the new third-party weather service into your application without modifying any of the existing application code that uses the weather data.

## Requirements

1. Your application code should continue to work without any changes
2. The third-party service cannot be modified (it's external)
3. The integration should be seamless and transparent
4. City and location represent the same concept

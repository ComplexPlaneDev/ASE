from adapter import WeatherServiceAdapter
from third_party_weather_service import ThirdPartyWeatherService

def main():
    city = "Berlin"

    # init new third party service
    third_party_api = ThirdPartyWeatherService()

    # init adapter
    weather_service = WeatherServiceAdapter(third_party_api)

    # use adapter to print the weather
    # function names remain unchanged, no code changes needed here
    print(weather_service.get_current_temperature(city))
    print(weather_service.get_weather_condition(city))
    print(f"Humidity Level: {weather_service.get_humidity_level(city)}%")

if __name__ == "__main__":
    main()

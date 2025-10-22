class WeatherServiceAdapter:
    def __init__(self, third_party_service):
        self.third_party_service = third_party_service

    def get_current_temperature(self, city):
        return self.third_party_service.fetch_temp(city)

    def get_weather_condition(self, city):
        return self.third_party_service.retrieve_conditions(city)

    def get_humidity_level(self, city):
        return self.third_party_service.get_humidity(city)

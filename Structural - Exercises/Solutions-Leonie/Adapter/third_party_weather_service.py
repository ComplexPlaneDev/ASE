class ThirdPartyWeatherService:
    # get temperature from third party service
    def fetch_temp(self, location):
        return f"Temperature in {location}: 22°C"

    # get weather conditions from third party service
    def retrieve_conditions(self, location):
        return "Sunny"

    # get humindity from third party service
    def get_humidity(self, location):
        return 65

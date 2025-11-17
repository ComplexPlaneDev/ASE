import java.util.List;

public interface WeatherDataProvider {
    WeatherData getCurrentWeather(String locationId) throws WeatherApiException;
    List<WeatherData> getForecast(String locationId, int days) throws WeatherApiException;
}

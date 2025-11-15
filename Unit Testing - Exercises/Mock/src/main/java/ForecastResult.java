import java.util.List;

public class ForecastResult {
    private boolean successful;
    private String message;
    private List<WeatherData> forecast;

    public ForecastResult(boolean successful, String message, List<WeatherData> forecast) {
        this.successful = successful;
        this.message = message;
        this.forecast = forecast;
    }

    // Getters
    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
    public List<WeatherData> getForecast() { return forecast; }
}

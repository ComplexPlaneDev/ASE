import java.time.LocalDateTime;

public class WeatherData {
    private String locationId;
    private double temperature;
    private double windSpeed;
    private double rainfall;
    private double humidity;
    private String conditions;
    private LocalDateTime timestamp;

    public WeatherData(String locationId, double temperature, double windSpeed,
                       double rainfall, double humidity, String conditions, LocalDateTime timestamp) {
        this.locationId = locationId;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.rainfall = rainfall;
        this.humidity = humidity;
        this.conditions = conditions;
        this.timestamp = timestamp;
    }

    // Getters
    public String getLocationId() { return locationId; }
    public double getTemperature() { return temperature; }
    public double getWindSpeed() { return windSpeed; }
    public double getRainfall() { return rainfall; }
    public double getHumidity() { return humidity; }
    public String getConditions() { return conditions; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

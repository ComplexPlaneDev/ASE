import java.util.List;

public class MonitoringResult {
    private boolean successful;
    private String message;
    private List<WeatherAlert> alerts;

    public MonitoringResult(boolean successful, String message, List<WeatherAlert> alerts) {
        this.successful = successful;
        this.message = message;
        this.alerts = alerts;
    }

    // Getters
    public boolean isSuccessful() { return successful; }
    public String getMessage() { return message; }
    public List<WeatherAlert> getAlerts() { return alerts; }
}

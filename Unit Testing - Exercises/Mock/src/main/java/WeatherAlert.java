import java.time.LocalDateTime;

public class WeatherAlert {
    private String alertId;
    private String locationId;
    private WeatherMonitoringService.AlertType type;
    private AlertPreferences.AlertSeverity severity;
    private String message;
    private LocalDateTime timestamp;

    public WeatherAlert(String alertId, String locationId, WeatherMonitoringService.AlertType type,
                        AlertPreferences.AlertSeverity severity, String message, LocalDateTime timestamp) {
        this.alertId = alertId;
        this.locationId = locationId;
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters
    public String getAlertId() { return alertId; }
    public String getLocationId() { return locationId; }
    public WeatherMonitoringService.AlertType getType() { return type; }
    public AlertPreferences.AlertSeverity getSeverity() { return severity; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

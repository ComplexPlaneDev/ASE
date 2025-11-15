import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * WeatherMonitoringService handles weather data collection and alert processing.
 * This class demonstrates dependencies that are perfect candidates for mocking:
 * - WeatherDataProvider (external weather API)
 * - AlertRepository (database storage)
 * - NotificationService (SMS/email alerts)
 * - MetricsCollector (system monitoring)
 */
public class WeatherMonitoringService {
    public enum AlertType {
        HIGH_TEMPERATURE, EXTREME_HEAT, EXTREME_COLD,
        SEVERE_WINDS, HURRICANE_FORCE_WINDS,
        HEAVY_RAIN, SNOW_STORM, THUNDERSTORM
    }

    private final WeatherDataProvider weatherDataProvider;
    private final AlertRepository alertRepository;
    private final NotificationService notificationService;
    private final MetricsCollector metricsCollector;

    public WeatherMonitoringService(WeatherDataProvider weatherDataProvider,
                                   AlertRepository alertRepository,
                                   NotificationService notificationService,
                                   MetricsCollector metricsCollector) {
        this.weatherDataProvider = weatherDataProvider;
        this.alertRepository = alertRepository;
        this.notificationService = notificationService;
        this.metricsCollector = metricsCollector;
    }

    /**
     * Processes weather data collection and alert generation:
     * 1. Fetches current weather data from external API
     * 2. Analyzes data for severe weather conditions
     * 3. Generates alerts if thresholds are exceeded
     * 4. Stores alerts in database
     * 5. Sends notifications to subscribers
     * 6. Records metrics for monitoring
     *
     * @param locationId The location to monitor
     * @return MonitoringResult containing processing status and any alerts generated
     */
    public MonitoringResult processWeatherData(String locationId) {
        if (locationId == null || locationId.trim().isEmpty()) {
            return new MonitoringResult(false, "Location ID cannot be null or empty", null);
        }

        try {
            // Step 1: Fetch weather data
            WeatherData currentWeather = weatherDataProvider.getCurrentWeather(locationId);
            if (currentWeather == null) {
                metricsCollector.recordFailedDataFetch(locationId);
                return new MonitoringResult(false, "No weather data available for location", null);
            }

            metricsCollector.recordSuccessfulDataFetch(locationId);

            // Step 2: Analyze for severe conditions
            List<WeatherAlert> alerts = analyzeWeatherConditions(currentWeather);

            if (alerts.isEmpty()) {
                metricsCollector.recordNoAlertsGenerated(locationId);
                return new MonitoringResult(true, "Weather monitoring completed - no alerts", null);
            }

            // Step 3: Store alerts in database
            List<WeatherAlert> savedAlerts = alertRepository.saveAlerts(alerts);

            // Step 4: Send notifications for severe alerts
            for (WeatherAlert alert : savedAlerts) {
                if (alert.getSeverity() == AlertPreferences.AlertSeverity.SEVERE || alert.getSeverity() == AlertPreferences.AlertSeverity.EXTREME) {
                    List<String> subscribers = alertRepository.getSubscribersForLocation(locationId);
                    notificationService.sendWeatherAlert(alert, subscribers);
                }
            }

            // Step 5: Record metrics
            metricsCollector.recordAlertsGenerated(locationId, alerts.size());

            return new MonitoringResult(true, "Weather monitoring completed - " + alerts.size() + " alerts generated", savedAlerts);

        } catch (WeatherApiException e) {
            metricsCollector.recordApiError(locationId, e.getMessage());
            return new MonitoringResult(false, "Weather API error: " + e.getMessage(), null);
        } catch (DatabaseException e) {
            metricsCollector.recordDatabaseError(locationId, e.getMessage());
            return new MonitoringResult(false, "Database error: " + e.getMessage(), null);
        } catch (NotificationException e) {
            // Alerts were saved, so processing was partially successful
            metricsCollector.recordNotificationError(locationId, e.getMessage());
            return new MonitoringResult(true, "Weather monitoring completed but notifications failed", null);
        } catch (Exception e) {
            metricsCollector.recordSystemError(locationId, e.getMessage());
            return new MonitoringResult(false, "System error: " + e.getMessage(), null);
        }
    }

    /**
     * Retrieves weather forecast for planning purposes
     */
    public ForecastResult getForecast(String locationId, int days) {
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }

        if (days <= 0 || days > 7) {
            throw new IllegalArgumentException("Days must be between 1 and 7");
        }

        try {
            List<WeatherData> forecast = weatherDataProvider.getForecast(locationId, days);
            metricsCollector.recordForecastRequest(locationId, days);
            return new ForecastResult(true, "Forecast retrieved successfully", forecast);
        } catch (WeatherApiException e) {
            metricsCollector.recordApiError(locationId, e.getMessage());
            return new ForecastResult(false, "Failed to retrieve forecast: " + e.getMessage(), null);
        }
    }

    /**
     * Gets historical weather alerts for a location
     */
    public List<WeatherAlert> getHistoricalAlerts(String locationId, LocalDateTime from, LocalDateTime to) {
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("Date range cannot be null");
        }

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date cannot be after to date");
        }

        try {
            return alertRepository.getAlertsInDateRange(locationId, from, to);
        } catch (DatabaseException e) {
            throw new RuntimeException("Failed to retrieve historical alerts: " + e.getMessage(), e);
        }
    }

    /**
     * Updates subscriber preferences for weather alerts
     */
    public boolean updateAlertSubscription(String locationId, String subscriberId, AlertPreferences preferences) {
        if (locationId == null || subscriberId == null || preferences == null) {
            return false;
        }

        try {
            alertRepository.updateSubscriberPreferences(locationId, subscriberId, preferences);
            metricsCollector.recordSubscriptionUpdate(locationId, subscriberId);
            return true;
        } catch (DatabaseException e) {
            metricsCollector.recordDatabaseError(locationId, e.getMessage());
            return false;
        }
    }

    private List<WeatherAlert> analyzeWeatherConditions(WeatherData weather) {
        List<WeatherAlert> alerts = new java.util.ArrayList<>();

        // Temperature alerts
        if (weather.getTemperature() > 40.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.EXTREME_HEAT,
                AlertPreferences.AlertSeverity.EXTREME,
                "Extreme heat warning: " + weather.getTemperature() + "°C",
                weather.getTimestamp()
            ));
        } else if (weather.getTemperature() > 35.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.HIGH_TEMPERATURE,
                AlertPreferences.AlertSeverity.SEVERE,
                "High temperature alert: " + weather.getTemperature() + "°C",
                weather.getTimestamp()
            ));
        }

        if (weather.getTemperature() < -20.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.EXTREME_COLD,
                AlertPreferences.AlertSeverity.EXTREME,
                "Extreme cold warning: " + weather.getTemperature() + "°C",
                weather.getTimestamp()
            ));
        }

        // Wind alerts
        if (weather.getWindSpeed() > 120.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.HURRICANE_FORCE_WINDS,
                AlertPreferences.AlertSeverity.EXTREME,
                "Hurricane force winds: " + weather.getWindSpeed() + " km/h",
                weather.getTimestamp()
            ));
        } else if (weather.getWindSpeed() > 90.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.SEVERE_WINDS,
                AlertPreferences.AlertSeverity.SEVERE,
                "Severe wind warning: " + weather.getWindSpeed() + " km/h",
                weather.getTimestamp()
            ));
        }

        // Precipitation alerts
        if (weather.getRainfall() > 50.0) {
            alerts.add(new WeatherAlert(
                generateAlertId(),
                weather.getLocationId(),
                AlertType.HEAVY_RAIN,
                AlertPreferences.AlertSeverity.MODERATE,
                "Heavy rainfall alert: " + weather.getRainfall() + " mm/h",
                weather.getTimestamp()
            ));
        }

        return alerts;
    }

    private String generateAlertId() {
        return "ALERT-" + System.currentTimeMillis();
    }
}

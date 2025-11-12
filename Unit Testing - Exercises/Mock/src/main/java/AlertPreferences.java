public class AlertPreferences {
    public enum AlertSeverity {
        LOW, MODERATE, SEVERE, EXTREME
    }

    private boolean enableTemperatureAlerts;
    private boolean enableWindAlerts;
    private boolean enablePrecipitationAlerts;
    private AlertSeverity minimumSeverity;

    public AlertPreferences(boolean enableTemperatureAlerts, boolean enableWindAlerts,
                            boolean enablePrecipitationAlerts, AlertSeverity minimumSeverity) {
        this.enableTemperatureAlerts = enableTemperatureAlerts;
        this.enableWindAlerts = enableWindAlerts;
        this.enablePrecipitationAlerts = enablePrecipitationAlerts;
        this.minimumSeverity = minimumSeverity;
    }

    // Getters
    public boolean isEnableTemperatureAlerts() { return enableTemperatureAlerts; }
    public boolean isEnableWindAlerts() { return enableWindAlerts; }
    public boolean isEnablePrecipitationAlerts() { return enablePrecipitationAlerts; }
    public AlertSeverity getMinimumSeverity() { return minimumSeverity; }
}

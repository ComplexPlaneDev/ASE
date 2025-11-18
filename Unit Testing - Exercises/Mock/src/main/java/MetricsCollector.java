public interface MetricsCollector {
    void recordSuccessfulDataFetch(String locationId);
    void recordFailedDataFetch(String locationId);
    void recordNoAlertsGenerated(String locationId);
    void recordAlertsGenerated(String locationId, int count);
    void recordForecastRequest(String locationId, int days);
    void recordSubscriptionUpdate(String locationId, String subscriberId);
    void recordApiError(String locationId, String error);
    void recordDatabaseError(String locationId, String error);
    void recordNotificationError(String locationId, String error);
    void recordSystemError(String locationId, String error);
}

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository {
    List<WeatherAlert> saveAlerts(List<WeatherAlert> alerts) throws DatabaseException;
    List<String> getSubscribersForLocation(String locationId) throws DatabaseException;
    List<WeatherAlert> getAlertsInDateRange(String locationId, LocalDateTime from, LocalDateTime to) throws DatabaseException;
    void updateSubscriberPreferences(String locationId, String subscriberId, AlertPreferences preferences) throws DatabaseException;
}

import java.util.List;

public interface NotificationService {
    void sendWeatherAlert(WeatherAlert alert, List<String> subscribers) throws NotificationException;
}

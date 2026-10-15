/**
 * WeatherStation - A Colleague in the Mediator pattern. Reports weather
 * conditions to the ATC (mediator). It never talks to aircraft directly.
 */
public class WeatherStation {

    private ATCMediator mediator;

    public void setMediator(ATCMediator mediator) {
        this.mediator = mediator;
    }

    /** Raises a weather-change event through the mediator. */
    public void reportWeather(WeatherCondition condition) {
        mediator.reportWeather(condition);
    }
}

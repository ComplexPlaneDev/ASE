import java.util.HashMap;
import java.util.Map;

/**
 * ConcreteSubject: holds the current price of each symbol and notifies
 * all registered watchers whenever any price changes.
 */
public class StockMarket extends Subject {

    private final Map<String, Double> prices = new HashMap<>();

    public void setPrice(String symbol, double price) {
        prices.put(symbol, price);
        notifyObservers();
    }

    public double getPrice(String symbol) {
        return prices.getOrDefault(symbol, 0.0);
    }

    public boolean hasSymbol(String symbol) {
        return prices.containsKey(symbol);
    }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StockTicker implements StockSubject{

    private final Map<String, Set<StockObserver>> observers = new HashMap<>();
    private final Map<String, Double> stockPrices = new HashMap<>();

    public void setStockPrice(String stockSymbol, double newPrice){
        System.out.println("Price Update for " + stockSymbol + " to $" + String.format("%.2f", newPrice)) ;

        double previousClose = this.stockPrices.getOrDefault(stockSymbol, 0.0);

        this.stockPrices.put(stockSymbol, newPrice);
        
        notifyObservers(stockSymbol, newPrice, previousClose);
        
    }
    @Override
    public void subscribe(String stockSymbol, StockObserver observer){
        observers.computeIfAbsent(stockSymbol, k -> new HashSet<>()).add(observer);
        System.out.println(observer.getClass().getSimpleName() + " is now subscribed to " + stockSymbol);

    }

    @Override
        public void unsubscribe(String stockSymbol, StockObserver observer){
            if (observers.containsKey(stockSymbol)) {
                observers.get(stockSymbol).remove(observer);        
                System.out.println(observer.getClass().getSimpleName() + " has now unsubscribed from " + stockSymbol);
            }

    }
    @Override
    public void notifyObservers(String stockSymbol, double newPrice, double previousClose){
        if (observers.containsKey(stockSymbol)) {
            for (StockObserver observer : observers.get(stockSymbol)) {
                observer.update(stockSymbol, newPrice, previousClose);
            }
        }
    }

   
    
}

public interface StockSubject {
    void subscribe(String stockSymbol, StockObserver observer);
    void unsubscribe(String stockSymbol, StockObserver observer);
    void notifyObservers(String stockSymbol, double newPrice, double previousClose);
    
}

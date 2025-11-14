public interface StockObserver {
    void update(String stockSymbol, double newPrice, double previousClose);
}
public class StockMarketMain {
    public static void main(String[] args) {
        StockTicker stockTicker = new StockTicker();

        StockObserver dashboard = new TradingDashboard("Main Dashboard");
        StockObserver mobileAlert = new MobileAlert("User123's Phone");
        StockObserver anotherDashboard = new TradingDashboard("Analyst Dashboard");

        stockTicker.setStockPrice("AAPL", 150.00);
        stockTicker.setStockPrice("GOOGL", 2800.00);

        System.out.println("\n--- Subscribing Observers ---");
        stockTicker.subscribe("AAPL", dashboard);
        stockTicker.subscribe("AAPL", mobileAlert);
        stockTicker.subscribe("GOOGL", anotherDashboard);
        
        stockTicker.setStockPrice("AAPL", 152.50);

        stockTicker.setStockPrice("GOOGL", 2825.00);

        System.out.println("\n--- Unsubscribing Mobile Alert from AAPL ---");
        stockTicker.unsubscribe("AAPL", mobileAlert);

        stockTicker.setStockPrice("AAPL", 151.75);
    }
    
}

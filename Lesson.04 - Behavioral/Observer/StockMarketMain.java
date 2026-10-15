/**
 * Example 2 - Stock Market Ticker.
 * Observers only react to the symbols they are interested in, and an
 * abstract observer class (StockWatcher) is shared by two concrete watchers.
 */
public class StockMarketMain {

    public static void main(String[] args) {
        System.out.println("=== Example 2: Stock Market Ticker ===");

        StockMarket market = new StockMarket();
        PriceAlertWatcher alert = new PriceAlertWatcher(market, 150.0, "ACME", "GLOBEX");
        AverageTrackerWatcher tracker = new AverageTrackerWatcher(market, "ACME", "GLOBEX", "INITECH");

        market.setPrice("ACME", 140.0);
        market.setPrice("GLOBEX", 95.0);
        market.setPrice("INITECH", 210.0);
        market.setPrice("ACME", 155.0); // crosses the alert threshold
    }
}

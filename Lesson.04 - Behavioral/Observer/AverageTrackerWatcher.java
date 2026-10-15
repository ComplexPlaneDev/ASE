/** ConcreteObserver: prints the average price of its watched symbols. */
public class AverageTrackerWatcher extends StockWatcher {

    public AverageTrackerWatcher(StockMarket market, String... symbols) {
        super(market, symbols);
    }

    @Override
    protected void onPriceChanged(String symbol, double price) {
        double sum = 0.0;
        int count = 0;
        for (String s : symbols) {
            if (market.hasSymbol(s)) {
                sum += market.getPrice(s);
                count++;
            }
        }
        System.out.printf("  [AverageTracker] average of %d watched stocks: %.2f%n",
                count, count == 0 ? 0.0 : sum / count);
    }
}

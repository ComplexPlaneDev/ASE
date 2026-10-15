/** ConcreteObserver: raises an alert when a watched stock crosses a threshold. */
public class PriceAlertWatcher extends StockWatcher {

    private final double threshold;

    public PriceAlertWatcher(StockMarket market, double threshold, String... symbols) {
        super(market, symbols);
        this.threshold = threshold;
    }

    @Override
    protected void onPriceChanged(String symbol, double price) {
        if (price >= threshold) {
            System.out.printf("  [Alert] %s at %.2f reached the %.2f target!%n",
                    symbol, price, threshold);
        }
    }
}

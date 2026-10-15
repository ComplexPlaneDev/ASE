/**
 * Abstract ConcreteObserver: keeps the reference to the market and the
 * list of symbols it is interested in. Subclasses decide how to react
 * to a price change - this also shows that observers may be abstract.
 */
public abstract class StockWatcher implements Observer {

    protected final StockMarket market;
    protected final String[] symbols;

    protected StockWatcher(StockMarket market, String... symbols) {
        this.market = market;
        this.symbols = symbols;
        market.attach(this); // self-registration idiom
    }

    @Override
    public void update() {
        for (String symbol : symbols) {
            if (market.hasSymbol(symbol)) {
                onPriceChanged(symbol, market.getPrice(symbol));
            }
        }
    }

    /** Called for every watched symbol that has a known price. */
    protected abstract void onPriceChanged(String symbol, double price);
}

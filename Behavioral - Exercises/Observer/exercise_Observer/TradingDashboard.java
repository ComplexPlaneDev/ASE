public class TradingDashboard implements StockObserver {
    private final String name;

    public TradingDashboard(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockSymbol, double newPrice, double previousClose){
        double change = newPrice - previousClose;
        double percentChange = (previousClose == 0) ? 0 : (change / previousClose) * 100;

        String sign = (change >= 0) ? "+" : "";

        System.out.printf("[%s] %s: $%.2f (%s$%.2f, %s%.2f%%)%n",
                name, stockSymbol, newPrice, sign, change, sign, percentChange);
    }
}

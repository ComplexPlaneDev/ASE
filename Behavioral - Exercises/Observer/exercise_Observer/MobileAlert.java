public class MobileAlert implements StockObserver {
    private final String deviceId;

    public MobileAlert(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void update(String stockSymbol, double newPrice, double previousClose) {
        double change = newPrice - previousClose;
        double percentChange = (previousClose == 0) ? 0 : (change / previousClose) * 100;

        String changeType = (percentChange >= 0) ? "increased" : "decreased";
        
        System.out.printf("[Mobile Alert %s] ALERT: %s %s by %.2f%%%n",
                deviceId, stockSymbol, changeType, Math.abs(percentChange));
    }
}

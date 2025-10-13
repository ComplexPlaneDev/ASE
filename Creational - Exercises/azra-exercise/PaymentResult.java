public class PaymentResult {
    private boolean success;
    private String message;

    public PaymentResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return success ? "Payment successful: " + message : "Payment failed: " + message;
    }
}

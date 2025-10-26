public class PayPalPayment implements Payment {
    @Override
    public PaymentResult processPayment(double amount) {
        return new PaymentResult(true, "Paid " + amount + " via PayPal.");
    }
}

public class CreditCardPayment implements Payment {
    @Override
    public PaymentResult processPayment(double amount) {
        return new PaymentResult(true, "Paid " + amount + " via Credit Card.");
    }
}


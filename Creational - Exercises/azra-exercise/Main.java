public class Main {
    public static void main(String[] args) {
        PaymentFactory factory = new PayPalFactory();
        Payment payment = factory.createPayment();
        PaymentResult result = payment.processPayment(100.0);
        System.out.println(result);
    }
}

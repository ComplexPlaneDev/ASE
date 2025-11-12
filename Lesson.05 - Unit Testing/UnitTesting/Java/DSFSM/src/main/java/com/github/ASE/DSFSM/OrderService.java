package com.github.ASE.DSFSM;

public class OrderService {
    private final PaymentGateway paymentGateway;
    private final EmailSender emailSender;
    private final OrderRepository orderRepository;
    private final Notifier notifier;
    private final Logger logger;

    public OrderService(PaymentGateway paymentGateway, EmailSender emailSender, OrderRepository orderRepository,
            Notifier notifier, Logger logger) {
        this.paymentGateway = paymentGateway;
        this.emailSender = emailSender;
        this.orderRepository = orderRepository;
        this.notifier = notifier;
        this.logger = logger;
    }

    public boolean placeOrder(String userId, double amount) {
        logger.info("Placing order for " + userId + ", $" + amount);

        boolean success = paymentGateway.charge(userId, amount);
        if (!success) {
            logger.error("Payment failed");
            return false;
        }

        Order order = new Order(userId, amount);
        orderRepository.save(order);
        emailSender.send(userId, "Order confirmed: $" + amount);
        notifier.notify(userId, "New order placed");

        return true;
    }
}

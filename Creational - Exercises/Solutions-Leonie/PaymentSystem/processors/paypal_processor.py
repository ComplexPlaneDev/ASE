from processors.payment_processor_interface import PaymentProcessorInterface

class PayPalProcessor(PaymentProcessorInterface):
    def process_payment(self, amount: float):
        print(f"Processing PayPal payment of ${amount:.2f}...")
        return f"PayPal payment of ${amount:.2f} completed."

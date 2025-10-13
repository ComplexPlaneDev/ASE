from processors.payment_processor_interface import PaymentProcessorInterface

class CreditCardProcessor(PaymentProcessorInterface):
    def process_payment(self, amount: float):
        print(f"Processing credit card payment of ${amount:.2f}...")
        return f"Credit Card payment of ${amount:.2f} completed."

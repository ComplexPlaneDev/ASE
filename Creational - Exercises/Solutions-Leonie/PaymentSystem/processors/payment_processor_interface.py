class PaymentProcessorInterface:
    def process_payment(self, amount: float):
        raise NotImplementedError
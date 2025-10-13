from processors.payment_processor_interface import PaymentProcessorInterface

class BankTransferProcessor(PaymentProcessorInterface):
    def process_payment(self, amount: float):
        print(f"Processing bank transfer of ${amount:.2f}...")
        return f"Bank transfer of ${amount:.2f} completed."

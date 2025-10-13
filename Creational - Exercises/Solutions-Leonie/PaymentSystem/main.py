from payment_processor_factory import PaymentProcessorFactory

def process_payment(processor_type: str, amount: float):
    processor = PaymentProcessorFactory.create_processor(processor_type)
    result = processor.process_payment(amount)
    print(result)


if __name__ == "__main__":
    process_payment("credit_card", 150.00)
    process_payment("paypal", 75.50)
    process_payment("bank_transfer", 300.00)

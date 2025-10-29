from processors.credit_card_processor import CreditCardProcessor
from processors.paypal_processor import PayPalProcessor
from processors.bank_transfer_processor import BankTransferProcessor
from processors.payment_processor_interface import PaymentProcessorInterface

class PaymentProcessorFactory:
    @staticmethod
    def create_processor(processor_type: str):
        processor_type = processor_type.lower()
        if processor_type == "credit_card":
            return CreditCardProcessor()
        elif processor_type == "paypal":
            return PayPalProcessor()
        elif processor_type == "bank_transfer":
            return BankTransferProcessor()
        else:
            raise ValueError(f"Unknown payment processor type: {processor_type}")

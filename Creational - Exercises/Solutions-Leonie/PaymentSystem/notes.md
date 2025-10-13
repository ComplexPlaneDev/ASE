### Design Pattern: Factory Method Pattern
- payment processors are different types of products that share a common interface, each processor is created by its own factory method
- client should be able to use any payment processor without knowing the implementation
- new payment methods should be added without changing existing code

### Idea
Client --> Payment Processor Factory (creates concrete payment processor) --> Payment Processor Interface (implemented by each payment processor) --> Concrete Payment Processors (f.ex. credit card, paypal, bank transfer)

#### class PaymentProcessorFactory
- create_processor(): creates a payment processor

#### class PaymentProcessorInterface
- process_payment(): processes payment and returns result

#### class CreditCardProcessor, PayPalProcessor, BankTransferProcessor
- each class implements the function from the interface
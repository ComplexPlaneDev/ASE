export {};

type Result<T, E> = Ok<T, E> | Error<T, E>;

interface Ok<T, E> {
    readonly tag: 'Ok';
    readonly value: T;
}

interface Error<T, E> {
    readonly tag: 'Err';
    readonly error: E;
}

const Ok = <T, E>(value: T): Result<T, E> => ({ tag: 'Ok', value });
const Err = <T, E>(error: E): Result<T, E> => ({ tag: 'Err', error });

// Utility to chain Results
const bindResult = <T, E, U>(
    result: Result<T, E>,
    f: (t: T) => Result<U, E>
): Result<U, E> => {
    if (result.tag === 'Ok') {
        return f(result.value);
    } else {
        return Err(result.error);
    }
};

// Error types
type OrderError =
    | { type: 'InvalidInput' }
    | { type: 'OutOfStock', productId: string }
    | { type: 'PaymentFailed' }
    | { type: 'EmailFailed' };

// Domain types
type ProductId = string;

type InventoryItem = {
    productId: ProductId;
    available: number;
};

type CartItem = {
    productId: ProductId;
    quantity: number;
};

type Order = {
    id: string;
    items: CartItem[];
};

// Step 1: Parse raw input into Order
const parseOrder = (raw: unknown): Result<Order, OrderError> => {
    if (
        typeof raw === 'object' &&
        raw !== null &&
        'id' in raw &&
        'items' in raw
    ) {
        const { id, items } = raw as any;
        if (typeof id === 'string' && Array.isArray(items)) {
            return Ok({ id, items });
        }
    }
    return Err({ type: 'InvalidInput' });
};

// Step 2: Validate order structure
const validateOrder = (order: Order): Result<Order, OrderError> => {
    if (!order.items || order.items.length === 0) {
        return Err({ type: 'InvalidInput' });
    }
    console.log("Order validated");
    return Ok(order);
};

// Step 3: Check inventory
const checkInventory = (inventory: Map<ProductId, InventoryItem>) => (order: Order): Result<Order, OrderError> => {
    for (const item of order.items) {
        const stock = inventory.get(item.productId);
        if (!stock || stock.available < item.quantity) {
            return Err({ type: 'OutOfStock', productId: item.productId });
        }
    }
    console.log("Inventory checked");
    return Ok(order);
};

// Step 4: Charge payment (simulated)
const chargePayment = (order: Order): Result<{ paymentId: string }, OrderError> => {
    // Simulate 20% failure rate
    if (Math.random() < 0.2) {
        return Err({ type: 'PaymentFailed' });
    }
    const paymentId = `PAY-${Date.now()}`;
    console.log(`Payment processed: ${paymentId}`);
    return Ok({ paymentId });
};

// Step 5: Update inventory
const updateInventory = (inventory: Map<ProductId, InventoryItem>, order: Order, paymentId: string): Result<string, OrderError> => {
    try {
        for (const item of order.items) {
            const stock = inventory.get(item.productId);
            if (stock && stock.available >= item.quantity) {
                inventory.set(stock.productId, {
                    ...stock,
                    available: stock.available - item.quantity
                });
            }
        }
        console.log("Inventory updated");
        return Ok(paymentId);
    } catch (err) {
        return Err({ type: 'InvalidInput' });
    }
};

// Step 6: Send email
const sendConfirmationEmail = ({ paymentId }: { paymentId: string }): Result<string, OrderError> => {
    // Simulate 10% failure
    if (Math.random() < 0.1) {
        return Err({ type: 'EmailFailed' });
    }

    console.log("Confirmation email sent");
    return Ok(paymentId);
};

// Sample inventory
const inventory = new Map([
    ['productA', { productId: 'productA', available: 10 }],
    ['productB', { productId: 'productB', available: 5 }]
]);

// Simulated raw input
const rawInput = {
    id: "ORD-123",
    items: [
        { productId: "productA", quantity: 2 },
        { productId: "productB", quantity: 1 }
    ]
};

// Run pipeline
const pipeline = (input: unknown): Result<string, OrderError> => {
    const inventoryCopy = new Map(inventory); // avoid mutation
    const checkStock = checkInventory(inventoryCopy);

    return bindResult(parseOrder(input), (order) =>
        bindResult(validateOrder(order), (validOrder) =>
            bindResult(checkStock(validOrder), (orderWithStock) =>
                bindResult(chargePayment(orderWithStock), (paymentData) =>
                    bindResult(updateInventory(inventoryCopy, order, paymentData.paymentId), (paymentId) =>
                        sendConfirmationEmail({ paymentId })
                    )
                )
            )
        )
    );
};

const result = pipeline(rawInput);

if (result.tag === 'Ok') {
    console.log("Order processed successfully:", result.value);
} else {
    console.error("Failed to process order:", result.error);
}
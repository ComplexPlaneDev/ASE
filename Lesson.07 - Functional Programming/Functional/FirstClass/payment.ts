#!/usr/bin/env ts-node

// -------------------------------
// 1. Define types
// -------------------------------

type PaymentMethod = (amount: number) => boolean;

interface Order {
    orderId: string;
    amount: number;
    paymentMethod: PaymentMethod;
}

// -------------------------------
// 2. Define payment strategies as functions (functions as values)
// -------------------------------

const payByCreditCard: PaymentMethod = (amount): boolean => {
    console.log(`Processing credit card payment of $${amount.toFixed(2)}...`);
    return true;
};

const payByPayPal: PaymentMethod = (amount): boolean => {
    console.log(`Processing PayPal payment of $${amount.toFixed(2)}...`);
    return true;
};

const payByApplePay: PaymentMethod = (amount): boolean => {
    console.log(`Processing Apple Pay payment of $${amount.toFixed(2)}...`);
    return amount <= 500;
};

// -------------------------------
// 3. Process order function (function accepts another function as argument)
// -------------------------------

const processOrder = (order: Order): void => {
    console.log(`Processing order: ${order.orderId}`);
    const success = order.paymentMethod(order.amount);
    if (success) {
        console.log(`Payment successful for order ${order.orderId}\n`);
    } else {
        console.log(`Payment failed for order ${order.orderId}\n`);
    }
};

// -------------------------------
// 4. Create and run some orders
// -------------------------------

const order1: Order = {
    orderId: 'A001',
    amount: 150,
    paymentMethod: payByCreditCard
};

const order2: Order = {
    orderId: 'A002',
    amount: 300,
    paymentMethod: payByPayPal
};

const order3: Order = {
    orderId: 'A003',
    amount: 600,
    paymentMethod: payByApplePay
};

processOrder(order1);
processOrder(order2);
processOrder(order3);

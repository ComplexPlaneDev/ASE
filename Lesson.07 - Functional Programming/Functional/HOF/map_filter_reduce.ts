#!/usr/bin/env ts-node

// -------------------------------
// 1. Define types
// -------------------------------

type TransactionType = 'income' | 'expense';

interface Transaction {
    id: string;
    description: string;
    amount: number;
    type: TransactionType;
    date: Date;
}

// -------------------------------
// 2. Sample data
// -------------------------------

const transactions: Transaction[] = [
    { id: 't1', description: 'Salary',        amount: 3000, type: 'income', date: new Date('2024-03-01') },
    { id: 't2', description: 'Rent',          amount: 1200, type: 'expense', date: new Date('2024-03-02') },
    { id: 't3', description: 'Groceries',     amount: 150,  type: 'expense', date: new Date('2024-03-05') },
    { id: 't4', description: 'Freelance Gig', amount: 500,  type: 'income', date: new Date('2024-03-10') },
    { id: 't5', description: 'Electricity',   amount: 80,   type: 'expense', date: new Date('2024-03-15') },
    { id: 't6', description: 'Dining Out',    amount: 75,   type: 'expense', date: new Date('2024-03-20') },
];

// -------------------------------
// 3. Use map, filter, reduce together
// -------------------------------

const a = transactions.filter((transaction) => transaction.type === 'expense');
console.table(a);
const b = a.map((transaction) => transaction.amount);
console.table(b);

const totalExpenses = transactions
    .filter((transaction) => transaction.type === 'expense')
    .map((transaction) => transaction.amount)
    .reduce((sum, amount) => sum + amount, 0);

// -------------------------------
// 4. Output result
// -------------------------------

console.log(`Total Expenses in March: $${totalExpenses.toFixed(2)}`);
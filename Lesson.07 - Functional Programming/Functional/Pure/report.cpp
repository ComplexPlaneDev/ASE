#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <iomanip>

using namespace std;

// -------------------------------
// 1. Define types
// -------------------------------

enum class TransactionType {
    Income,
    Expense
};

struct Transaction {
    string id;
    string description;
    double amount;
    TransactionType type;
    string category;
};

// -------------------------------
// 2. Pure Functions
// -------------------------------

// Filter by transaction type (income/expense)
vector<Transaction> filterByType(
    const vector<Transaction>& transactions,
    TransactionType type) {

    vector<Transaction> result;
    for (const auto& tx : transactions) {
        if (tx.type == type) {
            result.push_back(tx);
        }
    }
    return result;
}

// Sum total amount from list of transactions
double sumTransactions(const vector<Transaction>& transactions) {
    double total = 0.0;
    for (const auto& tx : transactions) {
        total += tx.amount;
    }
    return total;
}

// Group transactions by category
map<string, double> groupByCategory(
    const vector<Transaction>& transactions) {

    map<string, double> summary;

    for (const auto& tx : transactions) {
        summary[tx.category] += tx.amount;
    }

    return summary;
}

// Format category map into human-readable strings
vector<string> formatSummary(
    const map<string, double>& summary) {

    vector<string> lines;

    for (const auto& [category, amount] : summary) {
        lines.push_back(category + ": $" + to_string(amount));
    }

    return lines;
}

// Predict next month's spending based on current totals
map<string, double> predictNextMonth(
    const map<string, double>& summary) {

    map<string, double> prediction;

    for (const auto& [category, amount] : summary) {
        prediction[category] = amount * 1.1; // 10% increase assumption
    }

    return prediction;
}

int main(int, char **) {
    // Sample data - all input is immutable
    vector<Transaction> transactions = {
        {"T1", "Salary", 3000.0, TransactionType::Income, "income"},
        {"T2", "Rent", 1200.0, TransactionType::Expense, "housing"},
        {"T3", "Groceries", 150.0, TransactionType::Expense, "food"},
        {"T4", "Freelance Gig", 500.0, TransactionType::Income, "income"},
        {"T5", "Electricity", 80.0, TransactionType::Expense, "utilities"},
        {"T6", "Dining Out", 75.0, TransactionType::Expense, "food"}
    };

    // Use pure functions to transform data
    auto expenses = filterByType(transactions, TransactionType::Expense);
    auto income = filterByType(transactions, TransactionType::Income);

    double totalExpenses = sumTransactions(expenses);
    double totalIncome = sumTransactions(income);

    auto categorized = groupByCategory(expenses);
    auto predicted = predictNextMonth(categorized);

    auto formatted = formatSummary(categorized);
    auto predictedFormatted = formatSummary(predicted);

    // Output results
    cout << "Expense Summary:\n";
    for (const auto& line : formatted) {
        cout << " - " << line << "\n";
    }

    cout << fixed << setprecision(2);
    cout << "Total Expenses: $" << totalExpenses << "\n";
    cout << "Total Income: $" << totalIncome << "\n";

    cout << "\nPredicted Next Month Spending:\n";
    for (const auto& line : predictedFormatted) {
        cout << " - " << line << "\n";
    }

    return 0;
}

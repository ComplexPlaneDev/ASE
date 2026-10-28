package UnitTesting.domain;

/**
 * A bank account entity. Pure domain object with no external dependencies —
 * this is the kind of class we can often test with NO test doubles at all.
 */
public class Account {

    private final String id;
    private Money balance;

    public Account(String id, Money openingBalance) {
        this.id = id;
        this.balance = openingBalance;
    }

    public String id() {
        return id;
    }

    public Money balance() {
        return balance;
    }

    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Cannot deposit a negative amount");
        }
        balance = balance.plus(amount);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        if (amount.isGreaterThan(balance)) {
            throw new InsufficientFundsException(id, amount, balance);
        }
        balance = balance.minus(amount);
    }
}

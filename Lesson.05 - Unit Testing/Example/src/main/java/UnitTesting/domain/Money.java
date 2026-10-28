package UnitTesting.domain;

/**
 * A monetary amount in minor units (cents), kept deliberately simple for the lecture.
 * Immutable value object.
 */
public final class Money {

    private final long cents;

    private Money(long cents) {
        this.cents = cents;
    }

    public static Money ofCents(long cents) {
        return new Money(cents);
    }

    public static Money euros(long euros) {
        return new Money(euros * 100);
    }

    public Money plus(Money other) {
        return new Money(this.cents + other.cents);
    }

    public Money minus(Money other) {
        return new Money(this.cents - other.cents);
    }

    public boolean isGreaterThan(Money other) {
        return this.cents > other.cents;
    }

    public boolean isNegative() {
        return this.cents < 0;
    }

    public long asCents() {
        return cents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return cents == money.cents;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(cents);
    }

    @Override
    public String toString() {
        return String.format("%.2f EUR", cents / 100.0);
    }
}

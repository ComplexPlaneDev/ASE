package UnitTesting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import UnitTesting.domain.Account;
import UnitTesting.domain.InsufficientFundsException;
import UnitTesting.domain.Money;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CHAPTER 0 — Unit testing WITHOUT test doubles.
 *
 * The Account is a pure domain object: no external collaborators, so no doubles
 * are needed. This chapter establishes the vocabulary the rest of the lecture
 * builds on: the Arrange–Act–Assert structure and STATE verification.
 */
@DisplayName("Chapter 0 — Plain unit tests (no doubles, state verification)")
class Chapter0_PlainUnitTest {

    @Test
    @DisplayName("Example 1: deposit increases the balance")
    void deposit_increasesBalance() {
        // Arrange
        Account account = new Account("A1", Money.euros(100));
        // Act
        account.deposit(Money.euros(50));
        // Assert (state verification)
        assertEquals(Money.euros(150), account.balance());
    }

    @Test
    @DisplayName("Example 2: withdrawing more than the balance is rejected")
    void withdraw_beyondBalance_throws() {
        Account account = new Account("A1", Money.euros(100));

        assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(Money.euros(150)));
        // balance unchanged after the failed operation
        assertEquals(Money.euros(100), account.balance());
    }

    @Test
    @DisplayName("Example 3: a negative deposit is an illegal argument")
    void deposit_negative_throws() {
        Account account = new Account("A1", Money.euros(100));

        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(Money.ofCents(-1)));
    }
}

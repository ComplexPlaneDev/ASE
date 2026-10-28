package UnitTesting.ports;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;

/**
 * Notification port. Sending a real e-mail/SMS is slow and has side effects,
 * so this is a classic candidate for Spies and Mocks.
 */
public interface NotificationService {

    void notifyTransfer(Account from, Account to, Money amount);
}

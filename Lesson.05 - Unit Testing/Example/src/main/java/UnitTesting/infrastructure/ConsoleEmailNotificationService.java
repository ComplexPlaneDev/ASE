package UnitTesting.infrastructure;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.NotificationService;

/**
 * REAL implementation of {@link NotificationService}.
 *
 * In a real system this would open an SMTP connection and send an e-mail — a
 * slow operation with an irreversible side effect (a real message in someone's
 * inbox). For the lecture we "send" to the console, but the teaching point holds:
 *
 *   You do NOT want this running inside a unit test — it is slow, it has side
 *   effects you cannot assert on cleanly, and in reality it would spam a mailbox.
 *   That is why tests substitute a Spy (to record calls) or a Mock (to verify them).
 */
public class ConsoleEmailNotificationService implements NotificationService {

    @Override
    public void notifyTransfer(Account from, Account to, Money amount) {
        // Imagine: smtpClient.send(...). Here we print to stdout as the "side effect".
        System.out.printf(
                "[EMAIL SENT]  To: owner-of-%s  |  Subject: Transfer confirmation%n" +
                "              Body: %s was transferred from %s to %s.%n",
                from.id(), amount, from.id(), to.id());
    }
}

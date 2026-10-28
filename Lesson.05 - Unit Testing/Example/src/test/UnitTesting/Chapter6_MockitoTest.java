package UnitTesting;

import UnitTesting.doubles.InMemoryAccountRepository;

import UnitTesting.domain.Account;
import UnitTesting.domain.Money;
import UnitTesting.ports.AccountRepository;
import UnitTesting.ports.AuditClock;
import UnitTesting.ports.FraudPolicy;
import UnitTesting.ports.NotificationService;
import UnitTesting.service.TransferService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * CHAPTER 6 — The SAME concepts with a mocking framework (Mockito).
 *
 * Hand-written doubles teach the concepts; in practice a framework removes the
 * boilerplate. Note how one Mockito mock plays several roles depending on how
 * you configure it:
 *   - when(...).thenReturn(...)  → STUB behavior
 *   - verify(...)                → MOCK / SPY behavior
 */
@DisplayName("Chapter 6 — Mockito: stubbing and verification")
@ExtendWith(MockitoExtension.class)
class Chapter6_MockitoTest {

    @Mock AccountRepository repository;      // will be used as a stub
    @Mock NotificationService notifier;      // will be used as a mock/spy
    @Mock AuditClock clock;                  // stub
    @Mock FraudPolicy fraudPolicy;           // dummy here (path never calls it)

    @Test
    @DisplayName("Example 1: stub findById + verify the notification (mock)")
    void mockito_stubAndVerify() {
        when(repository.findById("A")).thenReturn(new Account("A", Money.euros(500)));
        when(repository.findById("B")).thenReturn(new Account("B", Money.euros(0)));
        when(clock.nowEpochMillis()).thenReturn(42L);

        var service = new TransferService(repository, notifier, clock, fraudPolicy);
        service.transfer("A", "B", Money.euros(100));

        // behavior verification
        verify(notifier).notifyTransfer(any(), any(), eq(Money.euros(100)));
        verify(repository, times(2)).save(any());
        verifyNoInteractions(fraudPolicy); // proves the dummy role
    }

    @Test
    @DisplayName("Example 2: argument matchers assert precisely what was persisted")
    void mockito_argumentMatchers() {
        when(repository.findById("A")).thenReturn(new Account("A", Money.euros(500)));
        when(repository.findById("B")).thenReturn(new Account("B", Money.euros(0)));

        var service = new TransferService(repository, notifier, clock, fraudPolicy);
        service.transfer("A", "B", Money.euros(100));

        verify(repository).save(argThat(acc -> acc.id().equals("A")
                && acc.balance().equals(Money.euros(400))));
        verify(repository).save(argThat(acc -> acc.id().equals("B")
                && acc.balance().equals(Money.euros(100))));
    }

    @Test
    @DisplayName("Example 3: a partial mock (spy) wraps a real Fake object")
    void mockito_spyWrapsRealObject() {
        // Mockito.spy wraps a REAL object: calls run for real unless overridden.
        InMemoryAccountRepository realFake = new InMemoryAccountRepository();
        realFake.seed(new Account("A", Money.euros(500)));
        realFake.seed(new Account("B", Money.euros(0)));
        AccountRepository spy = spy(realFake);

        var service = new TransferService(spy, notifier, () -> 0L, fraudPolicy);
        service.transfer("A", "B", Money.euros(100));

        // Real logic executed (state changed) AND interactions recorded (spy).
        verify(spy, times(2)).save(any());
        org.junit.jupiter.api.Assertions.assertEquals(
                Money.euros(400), spy.findById("A").balance());
    }
}

package com.github.ASE.DSFSM.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.ASE.DSFSM.EmailSender;
import com.github.ASE.DSFSM.Logger;
import com.github.ASE.DSFSM.Notifier;
import com.github.ASE.DSFSM.Order;
import com.github.ASE.DSFSM.OrderRepository;
import com.github.ASE.DSFSM.OrderService;
import com.github.ASE.DSFSM.PaymentGateway;

public class OrderServiceTest {
    // Dummy Logger (not used in test logic)
    static class DummyLogger implements Logger {
        @Override
        public void info(String msg) {
        }

        @Override
        public void error(String msg) {
        }
    }

    // Stub PaymentGateway: controllable result
    private PaymentGateway stubGateway(boolean shouldSucceed) {
        PaymentGateway stub = mock(PaymentGateway.class);
        when(stub.charge(anyString(), anyDouble())).thenReturn(shouldSucceed);
        return stub;
    }

    // Fake OrderRepository: stores orders in memory
    static class FakeOrderRepository implements OrderRepository {
        private final List<Order> orders = new ArrayList<>();

        @Override
        public void save(Order order) {
            orders.add(order);
        }

        @Override
        public List<Order> findAll() {
            return new ArrayList<>(orders);
        }
    }

    @Test
    void testOrderService_withAllTestDoubles() {
        // Arrange
        PaymentGateway stubGateway = stubGateway(true); // Stub

        FakeOrderRepository fakeRepo = new FakeOrderRepository(); // Fake

        EmailSender realEmailSender = new EmailSender() {
            private final List<String> sent = new ArrayList<>();

            @Override
            public void send(String to, String message) {
                sent.add(to + ": " + message);
            }

            public List<String> getSent() {
                return sent;
            }
        };
        EmailSender spyEmailSender = spy(realEmailSender); // Spy

        Notifier mockNotifier = mock(Notifier.class); // Mock

        Logger dummyLogger = new DummyLogger(); // Dummy

        OrderService service = new OrderService(stubGateway, spyEmailSender, fakeRepo, mockNotifier, dummyLogger);

        // Act
        boolean result = service.placeOrder("alice@example.com", 120.0);

        // Assert
        assertTrue(result);

        // Verify Fake stored the order
        List<Order> saved = fakeRepo.findAll();
        assertEquals(1, saved.size());
        assertEquals("alice@example.com", saved.get(0).userId);

        // Verify Spy executed real send() and recorded interaction
        verify(spyEmailSender).send("alice@example.com", "Order confirmed: $120.0");

        // Verify Mock was called
        verify(mockNotifier).notify("alice@example.com", "New order placed");
    }

    @Test
    void testOrderService_paymentFails_nothingHappens() {
        // Arrange
        PaymentGateway stubGateway = stubGateway(false); // FAILS! Stub

        FakeOrderRepository fakeRepo = new FakeOrderRepository(); // Fake

        EmailSender realEmailSender = new EmailSender() {
            private final List<String> sent = new ArrayList<>();

            @Override
            public void send(String to, String message) {
                sent.add(to + ": " + message);
            }

            public List<String> getSent() {
                return sent;
            }
        };
        EmailSender spyEmailSender = spy(realEmailSender); // Spy

        Notifier mockNotifier = mock(Notifier.class); // Mock

        Logger dummyLogger = new DummyLogger(); // Dummy

        OrderService service = new OrderService(stubGateway, spyEmailSender, fakeRepo, mockNotifier, dummyLogger);

        // Act
        boolean result = service.placeOrder("bob@example.com", 9999.0);

        // Assert
        assertFalse(result);

        // Order should NOT be saved
        assertEquals(0, fakeRepo.findAll().size());

        // Email should NOT be sent
        verify(spyEmailSender, never()).send(anyString(), anyString());

        // Notifier should NOT be called
        verify(mockNotifier, never()).notify(anyString(), anyString());
    }
}

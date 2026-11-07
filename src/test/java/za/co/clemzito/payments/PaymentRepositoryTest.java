package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    /**
     * Summary: Verifies that a payment can be saved and retrieved using its ID.
     */
    @Test
    void shouldSaveAndFindPaymentById() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        Payment payment = new Payment(
                "PAY-101",
                new BigDecimal("250.00"),
                PaymentMethod.CREDIT_CARD
        );

        repository.save(payment);

        Optional<Payment> result = repository.findById("PAY-101");

        assertTrue(result.isPresent());
        assertSame(payment, result.get());
    }

    /**
     * Summary: Verifies that saving a null payment throws a NullPointerException.
     */
    @Test
    void shouldRejectNullPayment() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.save(null)
        );

        assertEquals("Payment cannot be null", exception.getMessage());
    }

    /**
     * Summary: Verifies that searching for a non-existing payment returns an empty Optional.
     */
    @Test
    void shouldReturnEmptyWhenPaymentDoesNotExist() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        Optional<Payment> result = repository.findById("PAY-999");

        assertTrue(result.isEmpty());
    }

    /**
     * Summary: Verifies that the repository rejects payments with duplicate IDs.
     */
    @Test
    void shouldRejectDuplicatePaymentId() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        Payment firstPayment = new Payment(
                "PAY-200",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        Payment duplicatePayment = new Payment(
                "PAY-200",
                new BigDecimal("250.00"),
                PaymentMethod.DEBIT_CARD
        );

        repository.save(firstPayment);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(duplicatePayment)
        );

        assertEquals("Payment ID already exists: PAY-200", exception.getMessage());

        assertSame(firstPayment, repository.findById("PAY-200").orElseThrow());
    }

    /**
     * Summary: Verifies that the repository returns only payments matching the requested status.
     */
    @Test
    void shouldFindPaymentsByStatus() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        Payment successfulPayment = new Payment(
                "PAY-301",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        Payment failedPayment = new Payment(
                "PAY-302",
                new BigDecimal("200.00"),
                PaymentMethod.DEBIT_CARD
        );

        successfulPayment.setStatus(PaymentStatus.SUCCESSFUL);
        failedPayment.setStatus(PaymentStatus.FAILED);

        repository.save(successfulPayment);
        repository.save(failedPayment);

        List<Payment> result =
                repository.findByStatus(PaymentStatus.SUCCESSFUL);

        assertEquals(1, result.size());
        assertSame(successfulPayment, result.get(0));
    }

    /**
     * Summary: Verifies that searching by a status with no matching payments returns an empty list.
     */
    @Test
    void shouldReturnEmptyListWhenNoPaymentsMatchStatus() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();

        List<Payment> result =
                repository.findByStatus(PaymentStatus.FAILED);

        assertTrue(result.isEmpty());
    }
}
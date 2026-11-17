
package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the business operations performed by PaymentService.
 * Verifies payment processing outcomes, validation, and repository persistence.
 */
public class PaymentServiceTest {

    /**
     * Verifies that a payment is marked as successful when the processor
     * returns SUCCESSFUL.
     */
    @Test
    void shouldProcessPaymentSuccessfully() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> PaymentStatus.SUCCESSFUL
        );

        Payment payment = new Payment(
                "PAY-001",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        service.processPayment(payment);

        assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus());
    }

    /**
     * Verifies that a failed payment is marked as FAILED and saved
     * with the correct status in the repository.
     */
    @Test
    void shouldProcessPaymentAsFailed() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> PaymentStatus.FAILED
        );

        Payment payment = new Payment(
                "PAY-002",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        service.processPayment(payment);

        assertEquals(PaymentStatus.FAILED, payment.getStatus());
        assertEquals(
                PaymentStatus.FAILED,
                repository.findById("PAY-002").orElseThrow().getStatus()
        );
    }

    /**
     * Verifies that processing rejects a null payment.
     */
    @Test
    void shouldRejectNullPayment() {
        PaymentService service = new PaymentService(
                new InMemoryPaymentRepository(),
                payment -> PaymentStatus.SUCCESSFUL
        );

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> service.processPayment(null)
        );

        assertEquals("Payment cannot be null", exception.getMessage());
    }

    /**
     * Verifies that a successfully processed payment is saved
     * in the repository.
     */
    @Test
    void shouldSaveProcessedPaymentInRepository() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> PaymentStatus.SUCCESSFUL
        );

        Payment payment = new Payment(
                "PAY-004",
                new BigDecimal("150.00"),
                PaymentMethod.CREDIT_CARD
        );

        service.processPayment(payment);

        assertTrue(repository.findById("PAY-004").isPresent());
        assertEquals(
                PaymentStatus.SUCCESSFUL,
                repository.findById("PAY-004").orElseThrow().getStatus()
        );
    }

    /**
     * Verifies that an already processed payment cannot be processed again.
     */
    @Test
    void shouldRejectAlreadyProcessedPayment() {
        PaymentService service = new PaymentService(
                new InMemoryPaymentRepository(),
                payment -> PaymentStatus.SUCCESSFUL
        );

        Payment payment = new Payment(
                "PAY-005",
                new BigDecimal("200.00"),
                PaymentMethod.CREDIT_CARD
        );

        service.processPayment(payment);

        assertThrows(
                IllegalStateException.class,
                () -> service.processPayment(payment)
        );
    }

    /**
     * Verifies that a payment with FAILED status cannot be processed again.
     */
    @Test
    void shouldRejectReprocessingFailedPayment() {
        PaymentService service = new PaymentService(
                new InMemoryPaymentRepository(),
                payment -> PaymentStatus.SUCCESSFUL
        );

        Payment payment = new Payment(
                "PAY-006",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        payment.setStatus(PaymentStatus.FAILED);

        assertThrows(
                IllegalStateException.class,
                () -> service.processPayment(payment)
        );
    }

    /**
     * Verifies that the processor cannot return PENDING as a final result.
     */
    @Test
    void shouldRejectPendingAsProcessorResult() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> PaymentStatus.PENDING
        );

        Payment payment = new Payment(
                "PAY-007",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        assertThrows(
                IllegalStateException.class,
                () -> service.processPayment(payment)
        );

        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertFalse(repository.findById("PAY-007").isPresent());
    }

    /**
     * Verifies that a null result from the processor is rejected.
     */
    @Test
    void shouldRejectNullProcessorResult() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> null
        );

        Payment payment = new Payment(
                "PAY-008",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        assertThrows(
                NullPointerException.class,
                () -> service.processPayment(payment)
        );

        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertFalse(repository.findById("PAY-008").isPresent());
    }

    /**
     * Verifies that a processor exception prevents the payment from
     * being updated or saved.
     */
    @Test
    void shouldNotSavePaymentWhenProcessorThrowsException() {
        InMemoryPaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(
                repository,
                payment -> {
                    throw new IllegalStateException("Processor unavailable");
                }
        );

        Payment payment = new Payment(
                "PAY-009",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        assertThrows(
                IllegalStateException.class,
                () -> service.processPayment(payment)
        );

        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertFalse(repository.findById("PAY-009").isPresent());
    }

    /**
     * Verifies that PaymentService rejects a null processor dependency.
     */
    @Test
    void shouldRejectNullProcessor() {
        assertThrows(
                NullPointerException.class,
                () -> new PaymentService(
                        new InMemoryPaymentRepository(),
                        null
                )
        );
    }
}

package za.co.clemzito.payments;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Tests for the {@link PaymentService} class.
 */
public class PaymentServiceTest {
    @Test
    void shouldProcessPaymentSuccessfully() {
        Payment payment = new Payment(
                "PAY-001",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);
        paymentService.processPayment(payment);
        assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus());
    }

    @Test
    void shouldRejectPaymentWithZeroAmount() {
        Payment payment = new Payment(
                "PAY-002",
                new BigDecimal("0.00"),
                PaymentMethod.CREDIT_CARD
        );
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);

        paymentService.processPayment(payment);

        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }


    @Test
    void shouldRejectPaymentWithNegativeAmount() {
        Payment payment = new Payment(
                "PAY-003",
                new BigDecimal("-50.00"),
                PaymentMethod.DEBIT_CARD
        );

        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);

        paymentService.processPayment(payment);

        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void shouldRejectNullPayment() {
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> paymentService.processPayment(null)
        );

        assertEquals("Payment cannot be null", exception.getMessage());
    }

    @Test
    void shouldSaveProcessedPaymentInRepository() {
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);

        Payment payment = new Payment(
                "PAY-004",
                new BigDecimal("150.00"),
                PaymentMethod.CREDIT_CARD
        );

        paymentService.processPayment(payment);


        assertTrue(repository.findById("PAY-004").isPresent());

        assertEquals(
                PaymentStatus.SUCCESSFUL,
                repository.findById("PAY-004").orElseThrow().getStatus()
        );

    }

    @Test
    void shouldRejectAlreadyProcessedPayment() {
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(repository);

        Payment payment = new Payment(
                "PAY-005",
                new BigDecimal("200.00"),
                PaymentMethod.CREDIT_CARD
        );

        paymentService.processPayment(payment);

        assertThrows(
                IllegalStateException.class,
                () -> paymentService.processPayment(payment)
        );
    }
}
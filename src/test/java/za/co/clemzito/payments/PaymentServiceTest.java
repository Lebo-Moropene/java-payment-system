package za.co.clemzito.payments;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the {@link PaymentService} class.
 */
public class PaymentServiceTest {
    @Test
    void shouldProcessPaymentSuccessfully(){
        Payment payment = new Payment(
                "PAY-001",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );
        PaymentService paymentService  = new PaymentService();
        paymentService.processPayment(payment);
        assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus());
    }

    @Test
    void shouldRejectPaymentWithZeroAmount(){
        Payment payment = new Payment(
                "PAY-002",
                new BigDecimal("0.00"),
                PaymentMethod.CREDIT_CARD
        );
        PaymentService paymentService = new PaymentService();
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

        PaymentService paymentService = new PaymentService();

        paymentService.processPayment(payment);

        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void shouldRejectNullPayment() {
        PaymentService paymentService = new PaymentService();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> paymentService.processPayment(null)
        );

        assertEquals("Payment cannot be null", exception.getMessage());
    }

}

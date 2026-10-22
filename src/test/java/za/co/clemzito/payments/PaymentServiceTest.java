package za.co.clemzito.payments;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
}

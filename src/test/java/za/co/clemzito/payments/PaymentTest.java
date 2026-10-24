package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the {@link Payment} class.
 */
class PaymentTest {

    @Test
    void newPaymentShouldHavePendingStatus() {
        Payment payment = new Payment(
                "PAY-001",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        assertEquals(PaymentStatus.PENDING, payment.getStatus());
    }
}

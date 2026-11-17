
package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void shouldStorePaymentDetails() {
        Payment payment = new Payment(
                "PAY-001",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );

        assertEquals("PAY-001", payment.getPaymentId());
        assertEquals(new BigDecimal("100.00"), payment.getAmount());
        assertEquals(PaymentMethod.CREDIT_CARD, payment.getPaymentMethod());
    }

    @Test
    void shouldRejectBlankPaymentId() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment(" ", new BigDecimal("100.00"), PaymentMethod.CREDIT_CARD)
        );
    }

    @Test
    void shouldRejectNullPaymentId() {
        assertThrows(NullPointerException.class, () ->
                new Payment(null, new BigDecimal("100.00"), PaymentMethod.CREDIT_CARD)
        );
    }

    @Test
    void shouldRejectNullAmount() {
        assertThrows(NullPointerException.class, () ->
                new Payment("PAY-001", null, PaymentMethod.CREDIT_CARD)
        );
    }

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment(
                        "PAY-001",
                        new BigDecimal("0.00"),
                        PaymentMethod.CREDIT_CARD
                )
        );
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment(
                        "PAY-002",
                        new BigDecimal("-50.00"),
                        PaymentMethod.CREDIT_CARD

            )
        );
    }

    @Test
    void shouldRejectNullPaymentMethod() {
        assertThrows(NullPointerException.class, () ->
                new Payment("PAY-001", new BigDecimal("100.00"), null)
        );
    }

    @Test
    void shouldKeepPaymentDetailsUnchangedWhenStatusChanges(){
        Payment payment = new Payment(
                "PAY-006",
                new BigDecimal("250.00"),
                PaymentMethod.DEBIT_CARD
        );
        payment.setStatus(PaymentStatus.SUCCESSFUL);

        assertEquals("PAY-006", payment.getPaymentId());
        assertEquals(new BigDecimal("250.00"), payment.getAmount());
        assertEquals(PaymentMethod.DEBIT_CARD, payment.getPaymentMethod());
        assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus());
    }

    @Test
    void shouldRejectChangingSuccessfulPaymentToFailed(){
        Payment payment = new Payment(
                "PAY-007",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );
        payment.setStatus(PaymentStatus.SUCCESSFUL);

        assertThrows(IllegalStateException.class, () ->
                payment.setStatus(PaymentStatus.FAILED)
        );

    }

    @Test
    void shouldRejectChangingFailedPaymentToSuccessful() {
        Payment payment = new Payment(
                "PAY-008",
                new BigDecimal("100.00"),
                PaymentMethod.DEBIT_CARD
        );

        payment.setStatus(PaymentStatus.FAILED);

        assertThrows(IllegalStateException.class, () ->
                payment.setStatus(PaymentStatus.SUCCESSFUL)
        );
    }


}


package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link PaymentMethod} enum.
 */
class PaymentMethodTest {

    @Test
    void shouldContainThreePaymentMethods() {
        assertEquals(3, PaymentMethod.values().length);
    }

    @Test
    void shouldSupportCreditCard() {
        assertEquals(
                PaymentMethod.CREDIT_CARD,
                PaymentMethod.valueOf("CREDIT_CARD")
        );
    }

    @Test
    void shouldSupportDebitCard() {
        assertEquals(
                PaymentMethod.DEBIT_CARD,
                PaymentMethod.valueOf("DEBIT_CARD")
        );
    }

    @Test
    void shouldSupportEft() {
        assertEquals(
                PaymentMethod.EFT,
                PaymentMethod.valueOf("EFT")
        );
    }
}

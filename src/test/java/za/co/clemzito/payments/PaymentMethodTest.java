
package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentMethodTest {

    @Test
    void shouldContainCreditCardMethod() {
        assertEquals(
                PaymentMethod.CREDIT_CARD,
                PaymentMethod.valueOf("CREDIT_CARD")
        );
    }

    @Test
    void shouldContainDebitCardMethod() {
        assertEquals(
                PaymentMethod.DEBIT_CARD,
                PaymentMethod.valueOf("DEBIT_CARD")
        );
    }

    @Test
    void shouldContainEftMethod() {
        assertEquals(
                PaymentMethod.EFT,
                PaymentMethod.valueOf("EFT")
        );
    }
}

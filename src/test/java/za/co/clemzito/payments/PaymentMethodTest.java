package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that verify the constants exposed by the {@link PaymentMethod} enum.
 */
public class PaymentMethodTest {

    /**
     * Verifies that the debit card method can be resolved by name.
     */
    @Test
    void shouldHaveDebitCardMethod(){
        assertEquals(
                PaymentMethod.DEBIT_CARD,
                PaymentMethod.valueOf("DEBIT_CARD")
        );
    }

    /**
     * Verifies that the electronic funds transfer method can be resolved by name.
     */
    @Test
    void shouldHaveEftMethods(){
        assertEquals(
                PaymentMethod.EFT,
                PaymentMethod.valueOf("EFT")
        );
    }
}

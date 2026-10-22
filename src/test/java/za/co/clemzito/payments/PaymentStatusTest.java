package za.co.clemzito.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that verify the constants exposed by the {@link PaymentStatus} enum.
 */
public class PaymentStatusTest {

    /**
     * Verifies that every expected payment status is declared.
     */
    @Test
    void shouldContainAllPaymentStauses(){
        assertEquals(
                3,
                PaymentStatus.values().length
        );
    }

    /**
     * Verifies that the successful status can be resolved by name.
     */
    @Test
    void shouldHaveSuccessfulStatus() {
        assertEquals(
                PaymentStatus.SUCCESSFUL,
                PaymentStatus.valueOf("SUCCESSFUL")
        );
    }

    /**
     * Verifies that the failed status can be resolved by name.
     */
    @Test
    void shouldHaveFailedStatus() {
        assertEquals(
                PaymentStatus.FAILED,
                PaymentStatus.valueOf("FAILED")
        );
    }

}

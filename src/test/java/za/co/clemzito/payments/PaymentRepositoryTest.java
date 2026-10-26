package za.co.clemzito.payments;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentRepositoryTest {

    @Test
    void shouldSaveAndFindPaymentById(){
        PaymentRepository repository = new PaymentRepository();

        Payment payment = new Payment(
                "PAY-101",
                new BigDecimal("250.00"),
                PaymentMethod.CREDIT_CARD
        );

        repository.save(payment);

        Optional<Payment> result = repository.findById("PAY-101");

        assertTrue(result.isPresent());
        assertSame(payment, result.get());

    }

    @Test
    void shouldRejectNullPayment() {
        PaymentRepository repository = new PaymentRepository();

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> repository.save(null)
        );
        assertEquals("Payment cannot be null", exception.getMessage());
    }

    @Test
    void shouldReturnEmptyWhenPaymentDoesNotExist(){
        PaymentRepository repository = new PaymentRepository();

        Optional<Payment> result = repository.findById("PAY-999");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldRejectDuplicatePaymentId(){
        PaymentRepository repository = new PaymentRepository();

        Payment firstPayment = new Payment(
                "PAY-200",
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD
        );
        Payment duplicatePayment = new Payment(
                "PAY-200",
                new BigDecimal("250.00"),
                PaymentMethod.DEBIT_CARD
        );
        repository.save(firstPayment);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(duplicatePayment)
        );

        assertEquals("Payment ID already exists: PAY-200", exception.getMessage());

        assertSame(firstPayment, repository.findById("PAY-200").orElseThrow());

    }
}

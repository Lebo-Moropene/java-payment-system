package za.co.clemzito.payments;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SqlitePaymentRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldFindPaymentAfterReopeningRepository(){
        String databaseUrl = "jdbc:sqlite:"
                + tempDir.resolve("payments.db").toAbsolutePath();

        Payment payment = new Payment(
                "PAY-301",
                new BigDecimal("350.00"),
                PaymentMethod.CREDIT_CARD
        );

        SqlitePaymentRepository firstRepository =
                new SqlitePaymentRepository(databaseUrl);

        firstRepository.save(payment);

        SqlitePaymentRepository secondRepository =
                new SqlitePaymentRepository(databaseUrl);

        Payment retrievePayment = secondRepository
                .findById("PAY-301")
                .orElseThrow();

        assertEquals("PAY-301", retrievePayment.getPaymentId());
        assertEquals(new BigDecimal("350.00"), retrievePayment.getAmount());
        assertEquals(PaymentMethod.CREDIT_CARD, retrievePayment.getPaymentMethod());
        assertEquals(PaymentStatus.PENDING, retrievePayment.getStatus());

    }
}

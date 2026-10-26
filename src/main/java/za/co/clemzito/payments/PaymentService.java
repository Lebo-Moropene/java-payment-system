
package za.co.clemzito.payments;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Provides the business operations for working with payments.
 */
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository){
        this.repository = Objects.requireNonNull(
                repository,
                "Payment repository cannot be null"
        );
    }

    public void processPayment(Payment payment) {
        Objects.requireNonNull(payment, "Payment cannot be null");

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            payment.setStatus(PaymentStatus.FAILED);

        }else {
            payment.setStatus(PaymentStatus.SUCCESSFUL);
        }
        repository.save(payment);

    }
}

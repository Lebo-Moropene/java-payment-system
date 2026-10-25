
package za.co.clemzito.payments;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Provides the business operations for working with payments.
 */
public class PaymentService {

    public PaymentService() {
    }

    public void processPayment(Payment payment) {
        Objects.requireNonNull(payment, "Payment cannot be null");

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            payment.setStatus(PaymentStatus.FAILED);
            return;
        }

        payment.setStatus(PaymentStatus.SUCCESSFUL);
    }
}

package za.co.clemzito.payments;

import java.math.BigDecimal;
import java.util.concurrent.RecursiveTask;

/**
 * Provides the business operations for working with payments, such as
 * creating, validating and settling a {@link Payment}.
 */
public class PaymentService {

    /**
     * Creates a payment service with no payments processed yet.
     */
    public PaymentService() {
    }

    public void processPayment(Payment payment){
        if(payment.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            payment.setStatus(PaymentStatus.FAILED);
            return;
        }
        payment.setStatus(PaymentStatus.SUCCESSFUL);
    }
}

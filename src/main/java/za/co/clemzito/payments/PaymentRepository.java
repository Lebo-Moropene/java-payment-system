
package za.co.clemzito.payments;

import java.util.Optional;

/**
 * Defines the operations for saving and retrieving payments.
 */
public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findById(String paymentId);
}


package za.co.clemzito.payments;

import java.util.List;
import java.util.Optional;

/**
 * Defines the operations for saving and retrieving payments.
 */
public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findById(String paymentId);

    List<Payment> findAll();

    /**
     * Summary: Retrieves all payments matching the specified payment status.
     *
     * @param status the payment status used to filter payments
     * @return a list of payments matching the specified status
     */
    List<Payment> findByStatus(PaymentStatus status);
}

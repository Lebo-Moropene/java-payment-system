package za.co.clemzito.payments;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class InMemoryPaymentRepository implements PaymentRepository {

    /**
     * In- Memory repository for storing and retrieving payments
     */

    private final Map<String, Payment> payments = new HashMap<>();

/**
 * Saves a payment using its unques payment ID
 * @param payment the payment to save
 */

public void save(Payment payment){
Objects.requireNonNull(payment, "Payment cannot be null");

if (payments.containsKey(payment.getPaymentId())){
    throw new IllegalArgumentException(
            "Payment ID already exists: " + payment.getPaymentId()
    );
}
payments.put(payment.getPaymentId(), payment);

}

/**
 * Finds a payment by its ID
 *
 * @Param paymentId the payment identifier
 * @return the payment if found, otherwise an empty Optional
 */

public Optional<Payment> findById(String paymentId){
    return Optional.ofNullable(payments.get(paymentId));

}

}


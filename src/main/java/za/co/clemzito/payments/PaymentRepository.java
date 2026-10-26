package za.co.clemzito.payments;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory repository for storing and retrieving {@link Payment} records.
 * <p>
 * Payments are stored in a {@link Map} keyed by their payment identifier.
 * </p>
 */
public class PaymentRepository {

    /**
     * Internal storage mapping payment identifiers to {@link Payment} instances.
     */
    private final Map<String, Payment> payments = new HashMap<>();

    /**
     * Creates a new {@code PaymentRepository} instance.
     */
    public PaymentRepository() {
    }

    /**
     * Saves a payment to the repository.
     * <p>
     * Each payment must have a unique payment identifier. Attempting to save a
     * payment with an identifier that already exists will result in an exception.
     * </p>
     *
     * @param payment the payment to save
     * @throws NullPointerException     if the payment is {@code null}
     * @throws IllegalArgumentException if a payment with the same ID already exists
     */
    public void save(Payment payment) {
        Objects.requireNonNull(payment, "Payment cannot be null");

        if (payments.containsKey(payment.getPaymentId())) {
            throw new IllegalArgumentException(
                    "Payment ID already exists: " + payment.getPaymentId()
            );
        }

        payments.put(payment.getPaymentId(), payment);
    }

    /**
     * Finds a payment by its identifier.
     *
     * @param paymentId the identifier of the payment to find
     * @return an {@link Optional} containing the payment if found, or an empty
     *         {@link Optional} if no payment exists with the given identifier
     */
    public Optional<Payment> findById(String paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }

}

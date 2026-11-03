package za.co.clemzito.payments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryPaymentRepository implements PaymentRepository {

    /*
     * In-memory repository for storing and retrieving payments.
     */
    private final Map<String, Payment> payments = new HashMap<>();

    /*
     * Saves a payment using its unique payment ID.
     */
    @Override
    public void save(Payment payment) {
        Objects.requireNonNull(payment, "Payment cannot be null");

        if (payments.containsKey(payment.getPaymentId())) {
            throw new IllegalArgumentException(
                    "Payment ID already exists: " + payment.getPaymentId()
            );
        }

        payments.put(payment.getPaymentId(), payment);
    }

    /*
     * Finds a payment by its ID.
     */
    @Override
    public Optional<Payment> findById(String paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }

    /*
     * Retrieves all stored payments.
     */
    @Override
    public List<Payment> findAll() {
        return List.copyOf(payments.values());
    }
}
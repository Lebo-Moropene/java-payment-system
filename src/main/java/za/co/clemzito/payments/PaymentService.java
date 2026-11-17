
package za.co.clemzito.payments;

import java.util.Objects;

/**
 * Provides business operations for processing and managing payments.
 */
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentProcessor processor;

    /**
     * Creates a payment service with its required dependencies.
     *
     * @param repository the repository used to save payments
     * @param processor the component responsible for processing payments
     */
    public PaymentService(
            PaymentRepository repository,
            PaymentProcessor processor
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "Payment repository cannot be null"
        );

        this.processor = Objects.requireNonNull(
                processor,
                "Payment processor cannot be null"
        );
    }

    /**
     * Processes a pending payment, updates its status, and saves it.
     *
     * @param payment the payment to process
     * @throws NullPointerException if the payment or processor result is null
     * @throws IllegalStateException if the payment is already processed
     * or the processor returns PENDING
     */
    public void processPayment(Payment payment) {
        Objects.requireNonNull(payment, "Payment cannot be null");

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Payment has already been processed"
            );
        }

        PaymentStatus result = Objects.requireNonNull(
                processor.process(payment),
                "Payment processor returned null"
        );

        if (result == PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Payment processor must return a terminal status"
            );
        }

        payment.setStatus(result);
        repository.save(payment);
    }
}

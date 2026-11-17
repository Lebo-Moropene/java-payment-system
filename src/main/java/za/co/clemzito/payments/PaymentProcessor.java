package za.co.clemzito.payments;

/**
 * Defines the contract for processing a payment and determining its outcome.
 */
@FunctionalInterface
public interface PaymentProcessor {

    /**
     * Processes a payment and returns its resulting status.
     *
     * @param payment the payment to process
     * @return the resulting payment status
     */
    PaymentStatus process(Payment payment);
}

package za.co.clemzito.payments;

/**
 * The lifecycle states a {@link Payment} can be in.
 */
public enum PaymentStatus {
    /**
     * The payment has been created but has not been settled yet.
     */
    PENDING,

    /**
     * The payment completed successfully.
     */
    SUCCESSFUL,

    /**
     * The payment could not be completed.
     */
    FAILED
}

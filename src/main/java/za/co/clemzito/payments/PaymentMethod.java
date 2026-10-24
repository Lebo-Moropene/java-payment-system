
package za.co.clemzito.payments;

/**
 * The methods available for settling a Payment.
 */
public enum PaymentMethod {

    /**
     * The payment is funded by a credit card.
     */
    CREDIT_CARD,

    /**
     * The payment is funded by a debit card.
     */
    DEBIT_CARD,

    /**
     * The payment is made via electronic funds transfer.
     */
    EFT
}

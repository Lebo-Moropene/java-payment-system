
package za.co.clemzito.payments;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single payment, including its identifier, amount, payment
 * method and current lifecycle status.
 */
public class Payment {

    private final String paymentId;
    private final BigDecimal amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus status;

    public Payment(String paymentId, BigDecimal amount, PaymentMethod paymentMethod) {
        this.paymentId = Objects.requireNonNull(paymentId, "Payment ID cannot be null");

        if (paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be blank");
        }

        this.amount = Objects.requireNonNull(amount, "Payment amount cannot be null");
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        this.paymentMethod = Objects.requireNonNull(
                paymentMethod,
                "Payment method cannot be null"
        );
        this.status = PaymentStatus.PENDING;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = Objects.requireNonNull(status, "Payment status cannot be null");
    }
}

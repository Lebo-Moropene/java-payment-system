package za.co.clemzito.payments;

import java.math.BigDecimal;

public class Payment {

    private final String paymentId;
    private final BigDecimal amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus status;

    public Payment(String paymentId, BigDecimal amount, PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status){
        this.status = status;
    }

    public BigDecimal getAmount(){
        return amount;
    }
}

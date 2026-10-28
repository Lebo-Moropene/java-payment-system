
package za.co.clemzito;

import za.co.clemzito.payments.Payment;
import za.co.clemzito.payments.PaymentMethod;
import za.co.clemzito.payments.PaymentService;
import za.co.clemzito.payments.SqlitePaymentRepository;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Payment System");

        String databaseUrl = "jdbc:sqlite:payments.db";

        SqlitePaymentRepository repository =
                new SqlitePaymentRepository(databaseUrl);

        PaymentService paymentService =
                new PaymentService(repository);

        String paymentId = "PAY-001";

        var existingPayment = repository.findById(paymentId);

        if (existingPayment.isPresent()) {
            System.out.println("Payment already exists.");
            displayPayment(existingPayment.get());
            return;
        }

        Payment payment = new Payment(
                paymentId,
                new BigDecimal("250.00"),
                PaymentMethod.CREDIT_CARD
        );

        paymentService.processPayment(payment);

        System.out.println("Payment saved successfully.");
        displayPayment(payment);
    }

    private static void displayPayment(Payment payment) {
        System.out.println("Payment ID: " + payment.getPaymentId());
        System.out.println("Amount: R" + payment.getAmount());
        System.out.println("Method: " + payment.getPaymentMethod());
        System.out.println("Status: " + payment.getStatus());
    }
}

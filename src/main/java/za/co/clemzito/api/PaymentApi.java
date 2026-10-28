package za.co.clemzito.api;

import io.javalin.Javalin;
import za.co.clemzito.payments.Payment;
import za.co.clemzito.payments.PaymentMethod;
import za.co.clemzito.payments.PaymentRepository;
import za.co.clemzito.payments.PaymentService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import java.beans.JavaBean;

public class PaymentApi {
    private final Javalin app;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public PaymentApi(
            PaymentService paymentService,
            PaymentRepository paymentRepository
    ){
        this.paymentService = Objects.requireNonNull(paymentService);
        this.paymentRepository = Objects.requireNonNull(paymentRepository);

        app = Javalin.create();

        app.get("/health", ctx ->
                ctx.json(Map.of("status", "UP"))
        );

        app.post("/payments", ctx->{
            try{
                PaymentRequest request =
                        ctx.bodyAsClass(PaymentRequest.class);

                Payment payment = new Payment(
                        request.paymentId,
                        new BigDecimal(request.amount),
                        PaymentMethod.valueOf(request.paymentMethod)
                );
                paymentService.processPayment(payment);

                ctx.status(201).json(Map.of(
                        "paymentId", payment.getPaymentId(),
                        "amount", payment.getAmount(),
                        "paymentMethod", payment.getPaymentMethod(),
                        "status", payment.getStatus()
                ));
            }catch (IllegalArgumentException exception) {
                ctx.status(400).json(Map.of(
                        "error", "Invalid payment details"
                ));
            }
            });
        }

        public Javalin getApp(){
            return app;
        }

        public static class PaymentRequest{
            public String paymentId;
            public String amount;
            public String paymentMethod;

    }

}

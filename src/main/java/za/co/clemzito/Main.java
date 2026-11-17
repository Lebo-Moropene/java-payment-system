
package za.co.clemzito;

import io.javalin.Javalin;
import za.co.clemzito.api.PaymentApi;
import za.co.clemzito.payments.PaymentService;
import za.co.clemzito.payments.PaymentStatus;
import za.co.clemzito.payments.SqlitePaymentRepository;

/**
 * Starts the payment application.
 */
public class Main {

    /**
     * Creates the payment dependencies and starts the API server.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String databaseUrl = "jdbc:sqlite:payments.db";

        SqlitePaymentRepository repository =
                new SqlitePaymentRepository(databaseUrl);

        PaymentService paymentService =
                new PaymentService(
                        repository,
                        payment -> PaymentStatus.SUCCESSFUL
                );

        PaymentApi paymentApi =
                new PaymentApi(paymentService, repository);

        Javalin app = paymentApi.getApp();

        app.start(7000);

        System.out.println(
                "Payment API is running at http://localhost:7000"
        );
    }
}


package za.co.clemzito;

import io.javalin.Javalin;
import za.co.clemzito.api.PaymentApi;
import za.co.clemzito.payments.PaymentService;
import za.co.clemzito.payments.SqlitePaymentRepository;

public class Main {

    public static void main(String[] args) {
        String databaseUrl = "jdbc:sqlite:payments.db";

        SqlitePaymentRepository repository =
                new SqlitePaymentRepository(databaseUrl);

        PaymentService paymentService =
                new PaymentService(repository);

        PaymentApi paymentApi =
                new PaymentApi(paymentService, repository);

        Javalin app = paymentApi.getApp();

        app.start(7000);

        System.out.println(
                "Payment API is running at http://localhost:7000"
        );
    }
}

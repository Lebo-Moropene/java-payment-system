package za.co.clemzito.api;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpParser;
import org.junit.jupiter.api.Test;
import za.co.clemzito.payments.InMemoryPaymentRepository;
import za.co.clemzito.payments.PaymentRepository;
import za.co.clemzito.payments.PaymentService;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentApiTest {
    @Test
    void healthEndpointReturnOk() throws Exception{
        PaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(repository);
        Javalin app = new PaymentApi(service, repository).getApp();

        app.start(0);

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://localhost:" + app.port() + "/health"
                    ))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            assertEquals(200, response.statusCode());
            assertEquals("{\"status\":\"UP\"}", response.body());
        }finally {
            app.stop();
        }
        }

        @Test
    void createsPaymentAndReturnsCreated() throws Exception {
        PaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(repository);
        Javalin app = new PaymentApi(service, repository).getApp();

        app.start(0);

        try {
            String json = """
                    {
                      "paymentId": "PAY-API-001",
                      "amount": "150.00",
                      "paymentMethod": "DEBIT_CARD"
                      }
                      """;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://localhost:" + app.port() + "/payments"
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(201, response.statusCode());
            org.junit.jupiter.api.Assertions.assertTrue(
                    response.body().contains("\"status\":\"SUCCESSFUL\"")
            );
        }finally {
            app.stop();
        }
        }
}

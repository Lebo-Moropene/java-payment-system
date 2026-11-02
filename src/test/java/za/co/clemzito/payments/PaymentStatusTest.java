
package za.co.clemzito.payments;

import io.javalin.Javalin;
import org.junit.jupiter.api.Test;
import za.co.clemzito.api.PaymentApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests that verify the constants exposed by the PaymentStatus enum
 * and the retrieval of an existing payment through the REST API.
 */
public class PaymentStatusTest {

    /**
     * Verifies that every expected payment status is declared.
     */
    @Test
    void shouldContainAllPaymentStatuses() {
        assertEquals(
                3,
                PaymentStatus.values().length
        );
    }

    /**
     * Verifies that the successful status can be resolved by name.
     */
    @Test
    void shouldHaveSuccessfulStatus() {
        assertEquals(
                PaymentStatus.SUCCESSFUL,
                PaymentStatus.valueOf("SUCCESSFUL")
        );
    }

    /**
     * Verifies that the failed status can be resolved by name.
     */
    @Test
    void shouldHaveFailedStatus() {
        assertEquals(
                PaymentStatus.FAILED,
                PaymentStatus.valueOf("FAILED")
        );
    }

    /**
     * Verifies that an existing payment can be retrieved by its ID.
     */
    @Test
    void retrievesExistingPaymentById() throws Exception {
        PaymentRepository repository = new InMemoryPaymentRepository();
        PaymentService service = new PaymentService(repository);

        Javalin app = new PaymentApi(service, repository).getApp();

        app.start(0);

        try {
            String json = """
                    {
                        "paymentId": "PAY-GET-001",
                        "amount": "250.00",
                        "paymentMethod": "CREDIT_CARD"
                    }
                    """;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest createRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + app.port() + "/payments"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> createResponse = client.send(
                    createRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("POST status: " + createResponse.statusCode());
            System.out.println("POST body: " + createResponse.body());
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + app.port()
                            + "/payments/PAY-GET-001"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    getRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("PAY-GET-001"));

        } finally {
            app.stop();
        }
    }
}

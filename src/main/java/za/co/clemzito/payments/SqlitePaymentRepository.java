package za.co.clemzito.payments;
import za.co.clemzito.database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;





public class SqlitePaymentRepository {
    private final DatabaseConnection databaseConnection;

    public SqlitePaymentRepository(String databaseUrl){
        this.databaseConnection = new DatabaseConnection(databaseUrl);
        createTable();
    }

    private void createTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS payments(
                payment_id TEXT PRIMARY KEY,
                amount TEXT NOT NULL,
                payment_method TEXT NOT NULL,
                status TEXT NOT NULL
                )
                """;

        try (Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement()){

        statement.execute(sql);

        }catch (SQLException exception){
            throw new IllegalArgumentException(
                    "Could not create payments table",
                    exception
            );
        }
    }

    public void save(Payment payment){
        if(payment == null){
            throw new NullPointerException("Payment cannot be null");
        }

        String sql = """
                INSERT INTO payments (payment_id, amount, payment_method, status
                )
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, payment.getPaymentId());
            statement.setString(2, payment.getAmount().toPlainString());
            statement.setString(3, payment.getPaymentMethod().name());
            statement.setString(4, payment.getStatus().name());

            statement.executeUpdate();

        }catch (SQLException exception){
            if (exception.getMessage() != null
            && exception.getMessage().contains("UNIQUE constraint failed")){
                throw new IllegalArgumentException(
                        "Payment ID already exists: " + payment.getPaymentId(),
                        exception
                );
            }
            throw new IllegalArgumentException("Could not save payment", exception);

        }
    }
    public Optional<Payment> findById(String paymentId){
        String sql = """
                SELECT payment_id, amount, payment_method, status
                FROM payments
                WHERE payment_id = ?
                """;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

             statement.setString(1, paymentId);

            try (ResultSet resultSet = statement.executeQuery()){
                if(!resultSet.next()){
                    return Optional.empty();
                }

                Payment payment = new Payment(
                        resultSet.getString("payment_id"),
                        new BigDecimal(resultSet.getString("amount")),
                        PaymentMethod.valueOf(
                                resultSet.getString("payment_method")
                        )
                );

                payment.setStatus(
                        PaymentStatus.valueOf(
                                resultSet.getString("status")
                        )
                );

                return Optional.of(payment);
            }

        }catch (SQLException exception){
            throw  new IllegalArgumentException(
                    "Could not find payment",
                    exception
            );
        }
    }
}

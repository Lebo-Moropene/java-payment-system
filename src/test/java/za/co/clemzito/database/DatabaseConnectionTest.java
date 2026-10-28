package za.co.clemzito.database;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import za.co.clemzito.database.DatabaseConnection;

import java.awt.dnd.DropTarget;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;



public class DatabaseConnectionTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldOpenConnectionToSqliteDatabse() throws SQLException{
        String databaseUrl = "jdbc:sqlite:"
                + tempDir.resolve("payment.db").toAbsolutePath();

        DatabaseConnection databaseConnection =
                new DatabaseConnection((databaseUrl));

        try(Connection connection = databaseConnection.getConnection()){
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        }
    }
}

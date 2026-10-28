package za.co.clemzito.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection {

    private final String databaseUrl;

    public DatabaseConnection(String databaseUrl){
        this.databaseUrl = Objects.requireNonNull(
                databaseUrl,
                "Database URL cannot be null"
        );
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(databaseUrl);
    }
}

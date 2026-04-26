package me.cars.garage.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // 1. Load variables mn .env
                Dotenv dotenv = Dotenv.load();
                String url = dotenv.get("DB_URL");
                String user = dotenv.get("DB_USER");
                String password = dotenv.get("DB_PASSWORD");

                // 2. Register PostgreSQL Driver
                Class.forName("org.postgresql.Driver");

                // 3. Connect
                connection = DriverManager.getConnection(url, user, password);
                System.out.println(" Connected to PostgreSQL (Singleton Mode)");

            } catch (ClassNotFoundException e) {
                System.err.println("PostgreSQL Driver not found! Check your libraries.");
                throw new SQLException("Driver missing", e);
            }
        }
        return connection;
    }
}
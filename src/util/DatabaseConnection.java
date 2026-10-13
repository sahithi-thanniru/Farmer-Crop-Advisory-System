package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/farmer_advisory";

    private static final String USERNAME = "root";

    public static Connection getConnection() throws Exception {

        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new RuntimeException(
                    "DB_PASSWORD environment variable is not set."
            );
        }

        return DriverManager.getConnection(URL, USERNAME, password);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
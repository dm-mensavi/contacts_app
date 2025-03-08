package org.project.contactapp.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnection {

    private static final String URL = "jdbc:sqlite:contactdb.db"; // SQLite database file

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS person ("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + "lastname VARCHAR(45) NOT NULL, "
                + "firstname VARCHAR(45) NOT NULL, "
                + "nickname VARCHAR(45) NOT NULL, "
                + "phone_number VARCHAR(15) NULL, "
                + "address VARCHAR(200) NULL, "
                + "email_address VARCHAR(150) NULL, "
                + "birth_date DATE NULL"
                + ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
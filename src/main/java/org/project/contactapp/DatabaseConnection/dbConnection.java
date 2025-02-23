package org.project.contactapp.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {


        private static final String URL = "jdbc:mysql://localhost:3306/contactdb"; // Change to your DB
        private static final String USER = "root"; // Change to your MySQL username
        private static final String PASSWORD = "wonder1000"; // Change to your MySQL password

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }



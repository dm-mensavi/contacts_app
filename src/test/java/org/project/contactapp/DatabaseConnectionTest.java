//package org.project.contactapp;
//
//
//import org.junit.jupiter.api.Test;
//import org.project.contactapp.DatabaseConnection.dbConnection;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DatabaseConnectorTest {
//
//    @Test
//    void testDatabaseConnection() {
//        try (Connection conn = dbConnection.getConnection()) {
//            assertNotNull(conn, "Connection should not be null");
//            assertFalse(conn.isClosed(), "Connection should be open");
//        } catch (SQLException e) {
//            fail("Database connection failed: " + e.getMessage());
//        }
//    }
//}


package org.project.contactapp;

import org.junit.jupiter.api.Test;
import org.project.contactapp.DatabaseConnection.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class DatabaseConnectorTest {

    @Test
    void testDatabaseConnection() {
        try (Connection conn = dbConnection.getConnection()) {
            // AssertJ assertions
            assertThat(conn).isNotNull(); // Check that the connection is not null
            assertThat(conn.isClosed()).isFalse(); // Check that the connection is open
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage()); // Fail the test if an exception occurs
        }
    }
}
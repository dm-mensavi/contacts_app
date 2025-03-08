package org.project.contactapp;

import org.junit.jupiter.api.Test;
import org.project.contactapp.DatabaseConnection.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class DatabaseConnectionTest {

    @Test
    void testDatabaseConnection() {
        try (Connection conn = dbConnection.getConnection()) {
            assertThat(conn).isNotNull();
            assertThat(conn.isClosed()).isFalse();
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }
}
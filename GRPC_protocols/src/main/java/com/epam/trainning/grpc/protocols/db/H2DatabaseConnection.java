package com.epam.trainning.grpc.protocols.db;

import org.h2.tools.RunScript;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(H2DatabaseConnection.class.getName());

    static {
        try {
            initializeDatabase(getConnectionToDatabase());
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "SQL error", exception);
        }
    }

    public static Connection getConnectionToDatabase() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:testDb", "", "");

        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Could not set up connection", exception);
        }
        LOGGER.info("Connection set up completed");
        return connection;
    }

    public static void initializeDatabase(Connection conn) throws SQLException {
        final var resource = H2DatabaseConnection.class.getClassLoader().getResourceAsStream("data.sql");
        RunScript.execute(conn, new InputStreamReader(resource));
    }
}
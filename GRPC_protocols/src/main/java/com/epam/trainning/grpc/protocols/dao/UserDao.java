package com.epam.trainning.grpc.protocols.dao;

import com.epam.trainning.grpc.protocols.db.H2DatabaseConnection;
import com.epam.trainning.grpc.protocols.model.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public User getDetails(final String username) {
        final var user = new User();
        try {
            final var connection = H2DatabaseConnection.getConnectionToDatabase();
            final var prepareStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            prepareStatement.setString(1, username);
            final var result = prepareStatement.executeQuery();
            while (result.next()) {
                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setUsername(result.getString("username"));
                user.setAge(result.getInt("age"));
                user.setGender(result.getString("gender"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not execute query", e);
        }
        return user;
    }
}
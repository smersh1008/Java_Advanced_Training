package com.epam.trainning.grpc.protocols.server;

import com.epam.trainning.grpc.protocols.service.UserServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServer {
    private static final Logger LOGGER = Logger.getLogger(UserServer.class.getName());

    private Server server;

    public void startServer() {
        final var port = 50051;
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new UserServiceImpl())
                    .build()
                    .start();
            LOGGER.info("Server started on port 50051");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOGGER.info("Clean server shutdown in case JVM was shutdown.");
                try {
                    UserServer.this.stopServer();
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "Server shutdown interrupted.", e);
                }
            }));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server did not start.", e);
        }
    }

    public void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final var server = new UserServer();
        server.startServer();
        server.blockUntilShutdown();
    }
}

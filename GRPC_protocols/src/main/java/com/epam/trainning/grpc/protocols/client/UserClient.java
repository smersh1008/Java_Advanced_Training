package com.epam.trainning.grpc.protocols.client;

import com.epam.trainning.grpc.protocols.UserRequest;
import com.epam.trainning.grpc.protocols.UserServiceGrpc;
import com.epam.trainning.grpc.protocols.model.User;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class UserClient {
    private final ManagedChannel channel;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserClient(final String host, final int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public void printUserDetails(final String username) {
        final var userRequest = UserRequest.newBuilder().setUsername(username).build();
        final var userResponse = userServiceBlockingStub.getUserDetails(userRequest);
        final var user = new User();
        user.setId(userResponse.getId());
        user.setName(userResponse.getName());
        user.setUsername(userResponse.getUsername());
        user.setAge(userResponse.getAge());
        user.setGender(userResponse.getGender().toString());
        System.out.println(user);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        final var client = new UserClient("localhost", 50051);
        client.printUserDetails("christian");
        client.shutdown();
    }
}
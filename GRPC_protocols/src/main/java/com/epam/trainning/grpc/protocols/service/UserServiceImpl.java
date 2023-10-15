package com.epam.trainning.grpc.protocols.service;

import com.epam.trainning.grpc.protocols.Gender;
import com.epam.trainning.grpc.protocols.UserRequest;
import com.epam.trainning.grpc.protocols.UserResponse;
import com.epam.trainning.grpc.protocols.UserServiceGrpc;
import com.epam.trainning.grpc.protocols.dao.UserDao;
import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private static final UserDao USER_DAO = new UserDao();

    @Override
    public void getUserDetails(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        final var user = USER_DAO.getDetails(request.getUsername());

        final var response = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setName(user.getName())
                .setAge(user.getAge())
                .setGender(Gender.valueOf(user.getGender()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
package com.epam.trainning.rest.api.service;

import com.epam.trainning.rest.api.model.User;

import java.util.List;

public interface UserService {
    User create(final User user);

    User update(final User dto);

    void delete(final Long id);

    User getById(final Long id);

    List<User> getAll();
}
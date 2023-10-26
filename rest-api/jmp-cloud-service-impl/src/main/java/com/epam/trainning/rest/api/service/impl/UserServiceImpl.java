package com.epam.trainning.rest.api.service.impl;

import com.epam.trainning.rest.api.model.User;
import com.epam.trainning.rest.api.repository.UserRepository;
import com.epam.trainning.rest.api.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final VerificationService verificationService;

    @Override
    public User create(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(final User newUserData) {
        final var user = verificationService.verifyAndGetUser(newUserData.getId());
        if (StringUtils.isNotBlank(newUserData.getName())) {
            user.setName(newUserData.getName());
        }
        if (StringUtils.isNotBlank(newUserData.getSurname())) {
            user.setSurname(newUserData.getSurname());
        }
        if (newUserData.getBirthday() != null) {
            user.setBirthday(newUserData.getBirthday());
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(final Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public User getById(final Long id) {
        return verificationService.verifyAndGetUser(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
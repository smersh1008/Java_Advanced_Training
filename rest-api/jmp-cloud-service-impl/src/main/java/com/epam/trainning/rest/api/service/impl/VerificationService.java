package com.epam.trainning.rest.api.service.impl;

import com.epam.trainning.rest.api.model.Subscription;
import com.epam.trainning.rest.api.model.User;
import com.epam.trainning.rest.api.repository.SubscriptionRepository;
import com.epam.trainning.rest.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class VerificationService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public Subscription verifyAndGetSubscription(final Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Subscription with id='%d' is missing.", id)));
    }

    public User verifyAndGetUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id='%d' is missing.", id)));
    }
}
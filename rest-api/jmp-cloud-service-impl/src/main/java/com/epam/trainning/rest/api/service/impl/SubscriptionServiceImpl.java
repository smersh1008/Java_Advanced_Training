package com.epam.trainning.rest.api.service.impl;

import com.epam.trainning.rest.api.model.Subscription;
import com.epam.trainning.rest.api.repository.SubscriptionRepository;
import com.epam.trainning.rest.api.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final VerificationService verificationService;

    @Override
    public Subscription create(final Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription update(final Subscription newSubscriptionData) {
        final var user = verificationService.verifyAndGetUser(newSubscriptionData.getUser().getId());
        final var subscription = verificationService.verifyAndGetSubscription(newSubscriptionData.getId());
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void delete(final Long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
        }
    }

    @Override
    public Subscription getById(final Long id) {
        return verificationService.verifyAndGetSubscription(id);
    }

    @Override
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }
}
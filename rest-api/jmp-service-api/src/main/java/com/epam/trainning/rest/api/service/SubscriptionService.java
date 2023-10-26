package com.epam.trainning.rest.api.service;

import com.epam.trainning.rest.api.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription create(final Subscription dto);

    Subscription update(final Subscription dto);

    void delete(final Long id);

    Subscription getById(final Long id);

    List<Subscription> getAll();
}
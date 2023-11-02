package com.epam.trainning.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    public static final int MAX_ATTEMPT = 3;

    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginFailed(final String email) {
        int attempts;
        try {
            attempts = attemptsCache.get(email);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(email, attempts);
    }

    public void resumeLogin(final String email) {
        attemptsCache.put(email, 0);
    }

    public boolean isBlocked(final String email) {
        try {
            return attemptsCache.get(email) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    public List<String> getBlockedUsers() {
        return attemptsCache.asMap()
                .keySet().stream()
                .filter(this::isBlocked)
                .toList();
    }
}
package com.epam.trainning.component;

import com.epam.trainning.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        final var email = event.getAuthentication().getName();
        loginAttemptService.loginFailed(email);
    }
}
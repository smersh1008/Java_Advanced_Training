package com.epam.trainning.component;

import com.epam.trainning.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener< AuthenticationSuccessEvent > {
    private final LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        final var email = event.getAuthentication().getName();
        loginAttemptService.resumeLogin(email);
    }
}
package com.epam.trainning.configuration;

import com.epam.trainning.model.Authority;
import com.epam.trainning.model.User;
import com.epam.trainning.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
@AllArgsConstructor
public class UserConfig {
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunnerUser() {
        return args -> {
            final var user = new User(
                    "user1@abc.com",
                    passwordEncoder.encode("password"),
                    Collections.singleton(Authority.STANDARD)
            );
            final var admin = new User(
                    "user2@abc.com",
                    passwordEncoder.encode("password"),
                    Collections.singleton(Authority.STANDARD)
            );
            userRepository.saveAll(List.of(user, admin));
        };
    }
}
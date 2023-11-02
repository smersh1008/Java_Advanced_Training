package com.epam.trainning.service;

import com.epam.trainning.exception.BlockedUserException;
import com.epam.trainning.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        if (loginAttemptService.isBlocked(email)) {
            throw new BlockedUserException(String.format("'%s' is blocked for 5 mins.", email));
        }
        final var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s' not found.", email));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), true, true, true, true,
                user.getAuthorities());
    }
}

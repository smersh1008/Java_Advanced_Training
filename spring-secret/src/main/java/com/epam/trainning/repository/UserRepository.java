package com.epam.trainning.repository;

import com.epam.trainning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(final String email);
}
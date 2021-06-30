package com.bside.app.repository.user;

import com.bside.app.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByStoreName(String store_name);
    List<User> findAll();
}

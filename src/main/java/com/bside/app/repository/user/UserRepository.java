package com.bside.app.repository.user;

import com.bside.app.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(User user);
    User setStatusById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
}

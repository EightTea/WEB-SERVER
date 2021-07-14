package com.bside.app.repository.user;

import com.bside.app.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Integer id);
    List<User> findAll();
    User update(User user);
    void deleteById(User user);
    boolean existsById(Integer id);
}

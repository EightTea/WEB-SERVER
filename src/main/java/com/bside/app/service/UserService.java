package com.bside.app.service;

import com.bside.app.controller.TokenForm;
import com.bside.app.domain.JwtToken;
import com.bside.app.domain.User;

import java.util.Optional;


public interface UserService {

    Long join(User user);
    JwtToken login(Long id);
    void leave(Long id);
    JwtToken reissue(TokenForm tokenForm);
    Optional<User> findOne(Long id);
    Optional<User> getMyInfo();
}

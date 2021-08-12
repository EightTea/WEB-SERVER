package com.bside.app.service;

import com.bside.app.controller.user.TokenForm;
import com.bside.app.domain.JwtToken;
import com.bside.app.domain.User;

import java.util.Optional;


public interface UserService {

    Long join(User user);
    JwtToken login(Long id);
    Long updateStore(Long id, String storeName);
    void leave(Long id);
    JwtToken reissue(TokenForm tokenForm);
    Optional<User> findOne(Long id);
    User getMyInfo();
}

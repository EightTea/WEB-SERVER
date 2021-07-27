package com.bside.app.service;

import com.bside.app.controller.TokenForm;
import com.bside.app.controller.UserForm;
import com.bside.app.domain.JwtToken;

public interface AuthService {

    Long join(UserForm userForm);
    String login(Long id);
    void leave(Long id);
    JwtToken reissue(TokenForm tokenForm);
//    Integer verifyToken(String accessToken);
}

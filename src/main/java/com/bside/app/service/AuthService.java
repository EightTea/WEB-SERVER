package com.bside.app.service;

import com.bside.app.controller.TokenForm;
import com.bside.app.controller.UserForm;
import com.bside.app.domain.JwtToken;

public interface AuthService {

    Integer join(UserForm userForm);
    String login(Integer id);
    void leave(Integer id);
    JwtToken reissue(TokenForm tokenForm);
    Integer verifyToken(String accessToken);
}

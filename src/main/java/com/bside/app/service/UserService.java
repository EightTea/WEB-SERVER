package com.bside.app.service;

import com.bside.app.controller.TokenForm;
import com.bside.app.controller.UserForm;
import com.bside.app.domain.User;
import com.bside.app.jwt.*;
import com.bside.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserService {

    Optional<User> findOne(Integer id);
    Optional<User> getMemberInfo(Integer id);
    Optional<User> getMyInfo();
}

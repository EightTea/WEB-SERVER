package com.bside.app.service;

import com.bside.app.controller.user.TokenForm;
import com.bside.app.domain.Authority;
import com.bside.app.domain.JwtToken;
import com.bside.app.domain.RefreshToken;
import com.bside.app.domain.User;
import com.bside.app.jwt.TokenProvider;
import com.bside.app.repository.jwt.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired UserService userService;
    @Autowired RefreshTokenRepository tokenRepository;
    @Autowired TokenProvider tokenProvider;


    @Test
    @DisplayName("새로운 회원가입 시 DB 저장")
    void joinNewUser(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        User findUser = userService.findOne(user.getId()).get();

        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("탈퇴 회원일 시 user 테이블의 status만 변경")
    void joinDeletedUser(){
        // given
        User deletedUser = new User(1L, "가게 이름", 0, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        // 1. 탈퇴하기
        userService.join(deletedUser);
        userService.leave(deletedUser.getId());

        // 2. 재가입
        userService.join(deletedUser);
        User findUser = userService.findOne(deletedUser.getId()).get();

        // then
        assertThat(findUser.getStatus()).isEqualTo(1);
    }

    @Test
    @DisplayName("기존 회원일 시 로그인")
    void login(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        JwtToken token = userService.login(user.getId());
        RefreshToken refreshToken = tokenRepository.findByKey(user.getId()).get();

        // then
        assertThat(token.getAccessToken()).isNotNull();
        assertThat(token.getRefreshToken()).isEqualTo(refreshToken.getValue());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void leave(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        userService.leave(user.getId());
        User findUser = userService.findOne(user.getId()).get();

        // then
        assertThat(findUser.getStatus()).isEqualTo(0);
    }

    @Test
    @DisplayName("토큰 재발급")
    void reissue(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        JwtToken jwtToken = userService.login(user.getId());

        TokenForm tokenForm = new TokenForm(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        JwtToken reissueToken = userService.reissue(tokenForm);

        // then
        assertThat(jwtToken.getAccessToken()).isEqualTo(reissueToken.getAccessToken());
        assertThat(jwtToken.getRefreshToken()).isEqualTo(reissueToken.getRefreshToken());
    }

    @Test
    @DisplayName("회원 찾기")
    void findOne(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        User findUser = userService.findOne(user.getId()).get();

        // then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("현재 로그인한 사용자의 정보와 같아야 한다.")
    void getMyInfo(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userService.join(user);
        JwtToken token = userService.login(user.getId());

        // 현재 사용자 인증정보에 저장
        Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User getUser = userService.getMyInfo();

        // then
        assertThat(getUser).isEqualTo(user);
    }
}
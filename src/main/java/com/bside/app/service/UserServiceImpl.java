package com.bside.app.service;

import com.bside.app.domain.User;
import com.bside.app.repository.user.UserRepository;
import com.bside.app.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public Optional<User> findOne(Long userId){
        return userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMemberInfo(Long id){
        Optional<User> findUser = userRepository.findById(id);
        findUser.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
        return findUser;
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public Optional<User> getMyInfo(){

        Optional<User> findUser = userRepository.findById(SecurityUtil.getCurrentMemberId());
        findUser.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        return findUser;
    }

}

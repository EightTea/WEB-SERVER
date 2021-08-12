package com.bside.app.service;

import com.bside.app.controller.user.TokenForm;
import com.bside.app.domain.JwtToken;
import com.bside.app.domain.RefreshToken;
import com.bside.app.domain.User;
import com.bside.app.jwt.TokenProvider;
import com.bside.app.repository.jwt.RefreshTokenRepository;
import com.bside.app.repository.user.UserRepository;
import com.bside.app.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public Long join(User user){
        int validateResult = validateDuplicateUser(user.getId());
        // 비회원이면 회원가입으로 진행
        if(validateResult == 0) {
            log.debug("새로운 회원가입 : " + user.getId());
            return userRepository.save(user).getId();
        }
        // 탈퇴회원이었으면 상태변경
        else if(validateResult == 1){
            log.debug("탈퇴회원 상태 변경 : " + user.getId());
            return userRepository.update(user).getId();
        }
        else return user.getId();
    }

    /**
     * 사용자 검증
     * @param user_id
     * @return 비회원 : 0, 탈퇴회원 : 1, 회원 : 2
     */
    private int validateDuplicateUser(Long user_id){

        Optional<User> findId = userRepository.findById(user_id);
        // 비회원일 때
        if(findId.isEmpty()) return 0;
            // 탈퇴회원일 때
        else if(findId.get().getStatus() == 0) return 1;
            // 회원일 때
        else return 2;
    }


    @Transactional
    public JwtToken login(Long id){

        // ID통해 user 찾기
        Optional<User> findOne = this.findOne(id);
        return findOne.map(this::generateJwtToken).orElseThrow(() -> new IllegalStateException("해당 유저가 없습니다."));
    }

    @Transactional
    public Long updateStore(Long id, String storeName){
        User user = this.findOne(id).get();
        user.setStoreName(storeName);
        return userRepository.update(user).getId();
    }

    @Transactional
    public void leave(Long id) {
        Optional<User> findOne = userRepository.findById(id);
        findOne.map(User::getId).ifPresentOrElse(userRepository::setStatusById,
                () -> { throw new IllegalStateException("해당 유저가 없습니다."); });
    }

    @Transactional
    private JwtToken generateJwtToken(User user){
        // 1. Login ID/PW(이메일로 대체)를 기반으로 AuthenticationToken 생성
        final UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();

        // 2. 실제로 검증 (사용자 id 체크)이 이루어지는 부분
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = tokenProvider.generateTokenInfo(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(Long.parseLong(authentication.getName()))
                .value(jwtToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return jwtToken;
    }

    @Transactional
    public JwtToken reissue(TokenForm tokenForm){

        // 1. Refresh Token 검증
        if(!tokenProvider.validateToken(tokenForm.getRefreshToken())){
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member Id 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenForm.getAccessToken());

        // 3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(Long.parseLong(authentication.getName()))
                .orElseThrow(() ->new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는 지 검사
        if(!refreshToken.getValue().equals(tokenForm.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        JwtToken jwtToken = tokenProvider.generateTokenInfo(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return jwtToken;
    }

    @Transactional(readOnly = true)
    public Optional<User> findOne(Long userId){
        Optional<User> findId = userRepository.findById(userId);
        findId.orElseThrow(() -> new IllegalStateException("유저 정보가 없습니다."));
        return findId;
    }


    // 현재 SecurityContext 에 있는 유저 정보 가져오기(토큰에 있는 유저 정보)
    @Transactional(readOnly = true)
    public User getMyInfo(){

        Optional<User> findUser = userRepository.findById(SecurityUtil.getCurrentMemberId());
        findUser.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        return findUser.get();
    }
}

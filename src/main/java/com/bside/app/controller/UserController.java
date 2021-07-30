package com.bside.app.controller;

import com.bside.app.response.ApiResponse;
import com.bside.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 또는 로그인
     * @param userForm
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> joinOrLogin(@RequestBody UserForm userForm){
        Long joinId = userService.join(userForm.toUser());

        Map<String, Object> data = new HashMap<>();
        data.put("access_token", userService.login(joinId).getAccessToken());

        return new ResponseEntity<>(new ApiResponse("회원가입/로그인 성공", data),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> leave(@PathVariable Long id){
        userService.leave(id);
        return new ResponseEntity<>(new ApiResponse(200, "회원 탈퇴"), HttpStatus.OK);
    }

    /**
     * 토큰 재발급
     * @param tokenForm
     * @return
     */
    @PostMapping("/reissue")
    public ApiResponse reissue(@RequestBody TokenForm tokenForm){
        return new ApiResponse(200, "토큰 재발급 성공", userService.reissue(tokenForm));
    }

    /**
     * 토큰에 있는 id를 기반한 유저 정보 반환(테스트)
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyMemberInfo(){
        return ResponseEntity.ok(userService.getMyInfo());
    }
}

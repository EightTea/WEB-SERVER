package com.bside.app.controller;

import com.bside.app.response.ApiResponse;
import com.bside.app.service.AuthService;
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

    private final AuthService authService;

    /**
     * 회원가입 또는 로그인
     * @param userForm
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> joinOrLogin(@RequestBody UserForm userForm){
        Long joinId = authService.join(userForm);

        Map<String, Object> data = new HashMap<>();
        data.put("access_token", authService.login(joinId));

        return new ResponseEntity<>(new ApiResponse("회원가입/로그인 성공", data),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> leave(@PathVariable Long id){
        authService.leave(id);
        return new ResponseEntity<>(new ApiResponse(200, "회원 탈퇴"), HttpStatus.OK);
    }

    /**
     * 토큰 재발급
     * @param tokenForm
     * @return
     */
    @PostMapping("/reissue")
    public ApiResponse reissue(@RequestBody TokenForm tokenForm){
        return new ApiResponse(200, "토큰 재발급 성공", authService.reissue(tokenForm));
    }
}

package com.bside.app.controller;

import com.bside.app.response.ApiResponse;
import com.bside.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthService authService;

    /**
     * 회원가입
     * @param userForm
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> join(@RequestBody UserForm userForm){
        Long joinId = authService.join(userForm);

        JSONObject data = new JSONObject();
        data.put("access_token", authService.login(joinId));

        return new ResponseEntity<>(new ApiResponse("회원가입 성공", data.toString()),HttpStatus.OK);
    }

    /**
     * 로그인
     * @param id
     */
    @GetMapping("/{id}")
    public ApiResponse login(@PathVariable Long id){
        JSONObject data = new JSONObject();
        data.put("access_token", authService.login(id));

        return new ApiResponse(200, "로그인 성공", data.toString());
    }

    @DeleteMapping("/{id}")
    public ApiResponse leave(@PathVariable Long id){
        authService.leave(id);
        return new ApiResponse(200, "회원 탈퇴");
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

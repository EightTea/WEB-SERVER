package com.bside.app.controller.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenForm {
    private String accessToken;
    private String refreshToken;
}

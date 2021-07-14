package com.bside.app.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenForm {
    private String accessToken;
    private String refreshToken;
}

package com.bside.app.domain;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtToken {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;

}

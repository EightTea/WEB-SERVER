package com.bside.app.controller;


import com.bside.app.domain.User;
import com.bside.app.domain.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {

    private int id;
    private String store_name;
    private String email;
    private String nick_name;
    private int gender;
    private String year;

    public User toUser(){
        return User.builder()
                .id(id)
                .store_name(store_name)
                .status(1)
                .email(email)
                .nick_name(nick_name)
                .gender(gender)
                .year(year)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(id, email);
    }
}

package com.bside.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
@Entity
public class User {

    @Id
    private Integer id;
    private String store_name;
    private Integer status;
    private String email;
    private String nick_name;
    private Integer gender;
    private String year;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public User(Integer id, String store_name, Integer status, String nick_name, String email, Integer gender, String year, Authority authority){
        this.id = id;
        this.store_name = store_name;
        this.status = status;
        this.nick_name = nick_name;
        this.email = email;
        this.gender = gender;
        this.year = year;
        this.authority = authority;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(id, email);
    }
}

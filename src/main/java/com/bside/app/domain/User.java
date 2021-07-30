package com.bside.app.domain;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
@Entity
public class User {

    @Id
    private Long id;
    private String store_name;
    private Integer status;
    private String email;
    private String nickName;
    private Integer gender;
    private String year;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public User(Long id, String store_name, Integer status, String nick_name, String email, Integer gender, String year, Authority authority){
        this.id = id;
        this.store_name = store_name;
        this.status = status;
        this.nickName = nick_name;
        this.email = email;
        this.gender = gender;
        this.year = year;
        this.authority = authority;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(id, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", store_name='" + store_name + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", year='" + year + '\'' +
                ", authority=" + authority +
                '}';
    }
}

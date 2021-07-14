package com.bside.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    @Column(name="`key`")
    private Long key; // id
    private String value; // refresh Token 문자열

    public RefreshToken updateValue(String token){
        this.value = value;
        return this;
    }

    @Builder
    public RefreshToken(Long key, String value){
        this.key = key;
        this.value = value;
    }
}

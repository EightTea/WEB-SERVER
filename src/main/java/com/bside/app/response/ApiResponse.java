package com.bside.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {

    private int code = 200;
    private String msg = "OK";
    private Object data = null;

    public ApiResponse(String message, Object data){
        this.msg = message;
        this.data = data;
    }

    public ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

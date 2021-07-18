package com.bside.app.controller.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter @Setter
public class SurveyForm {

    // case 1 file 객체를 questionRequest class 안에 선언
    // data 보내는 형식 : question[0].file = { multipart 객체 }
    private String title;
    private String content;
    private QuestionRequest[] question;

    @Data
    static class QuestionRequest{
        // private int no;
        private String content;
        private MultipartFile file;
    }
}


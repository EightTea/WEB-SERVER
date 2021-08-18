package com.bside.app.controller.api.survey;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerForm {

    private String title;
    private AnswerRequest[] answer;

    @Data
    class AnswerRequest{
        private Long questionId;
        private String commment;
    }
}

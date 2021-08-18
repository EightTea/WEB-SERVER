package com.bside.app.controller.api.survey;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class AnswerForm {
    private Long questionId;
    private String comment;
}

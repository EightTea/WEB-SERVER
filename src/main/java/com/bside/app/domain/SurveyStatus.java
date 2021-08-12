package com.bside.app.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum SurveyStatus {
    IN_PROGRESS, PENDING, END; // 진행중/작성중/종료

    @JsonCreator
    public static SurveyStatus from(String status){
        return SurveyStatus.valueOf(status.toUpperCase(Locale.ROOT));
    }
}

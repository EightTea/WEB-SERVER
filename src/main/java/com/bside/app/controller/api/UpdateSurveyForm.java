package com.bside.app.controller.api;

import com.bside.app.domain.SurveyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UpdateSurveyForm {
    private SurveyStatus status;
}

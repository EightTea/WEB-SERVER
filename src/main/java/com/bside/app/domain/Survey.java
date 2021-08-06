package com.bside.app.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private SurveyStatus status;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String title;

    private String content;
    private String qrUrl;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question){
        questions.add(question);
        question.setSurvey(this);
    }

    public static Survey createSurvey(
            String surveyTitle, String surveyContent
            , Long userId, List<Question> questionList) {

        Survey survey = new Survey();
        survey.setTitle(surveyTitle);
        survey.setContent(surveyContent);
        survey.setUserId(userId); // TODO token통해 가져와야함

        for( Question question : questionList){
            survey.addQuestion(question);
        }

        survey.setStartDate( LocalDateTime.now() );
        survey.setStatus(SurveyStatus.IN_PROGRESS);

        return survey;
    }

}

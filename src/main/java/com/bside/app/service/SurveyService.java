package com.bside.app.service;

import com.bside.app.controller.api.SurveyForm;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.repository.question.QuestionRepository;
import com.bside.app.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long survey(String surveyTitle, String surveyContent
            ,Long userId, List<Question> questionList){

        // question 저장
        for( int i = 0 ; i < questionList.size() ; i ++ ){
            questionRepository.save(questionList.get(i));
        }

        Survey survey = Survey.createSurvey(surveyTitle, surveyContent, userId, questionList);

        surveyRepository.save(survey);

        return survey.getId();
    }
}

package com.bside.app.service;

import com.bside.app.controller.api.SurveyForm;
import com.bside.app.domain.JwtToken;
import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.jwt.TokenProvider;
import com.bside.app.repository.question.QuestionRepository;
import com.bside.app.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public List<Survey> findServeys(Integer userId){

        List<Survey> surveyList = surveyRepository.findAll(userId);
        return surveyList;
    }

    @Transactional
    public String findQrUrl(Integer surveyId){

        Optional<Survey> qrUrlById = surveyRepository.findQrUrlById(surveyId);
        qrUrlById.orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 설문입니다.");
        });

        return qrUrlById.get().getQrUrl();
    }
}

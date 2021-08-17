package com.bside.app.service;

import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.domain.SurveyStatus;
import com.bside.app.repository.question.QuestionRepository;
import com.bside.app.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final UserService userService;
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
    public Survey findById(Long surveyId){
        return surveyRepository.findById(surveyId);
    }

    @Transactional
    public List<Survey> findServeys(Long userId){

        List<Survey> surveyList = surveyRepository.findAllByUserId(userId);
        return surveyList;
    }

    @Transactional
    public String findQrUrl(Long surveyId){

        Optional<Survey> qrUrlById = surveyRepository.findQrUrlById(surveyId);
        qrUrlById.orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 설문입니다.");
        });

        return qrUrlById.get().getQrUrl();
    }

    @Transactional
    public Integer updateSurveyStatus(Long surveyId, SurveyStatus status){
        validateSurvey(surveyId);
        return surveyRepository.updateStatusById(surveyId, status);
    }

    @Transactional
    public void validateSurvey(Long surveyId){
        Long userId = userService.getMyInfo().getId();
        surveyRepository.existsByIdAndUserId(surveyId, userId)
                .orElseThrow(() -> {
                    throw new IllegalStateException("유효한 설문조사 아이디를 입력해주세요.");
                });
    }
}

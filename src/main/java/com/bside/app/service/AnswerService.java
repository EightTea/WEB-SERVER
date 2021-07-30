package com.bside.app.service;

import com.bside.app.domain.Answer;
import com.bside.app.repository.answer.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Page<Answer> findAnswers(Long serveyId, Long questionId, Pageable pageable){
        Page<Answer> answers = answerRepository.findBySurveyIdAndQuestionId(serveyId, questionId, pageable);
        return answers;
    }
}

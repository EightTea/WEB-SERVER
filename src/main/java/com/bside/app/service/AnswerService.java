package com.bside.app.service;

import com.bside.app.domain.Answer;
import com.bside.app.repository.answer.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private AnswerRepository answerRepository;

    public List<Answer> findAnswers(Integer serveyId, Integer questionId){
        return answerRepository.findAll(serveyId, questionId);
    }
}

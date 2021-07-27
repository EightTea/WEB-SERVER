package com.bside.app.service;

import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import com.bside.app.jwt.TokenProvider;
import com.bside.app.repository.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TokenProvider tokenProvider;

    public List<Question> findQuestions(Long serveyId){

        List<Question> questionList = questionRepository.findAll(serveyId);

        return questionList;
    }
}

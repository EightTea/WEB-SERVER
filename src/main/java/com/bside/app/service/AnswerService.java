package com.bside.app.service;

import com.bside.app.domain.Answer;
import com.bside.app.repository.answer.AnswerRepository;
import com.bside.app.repository.seq.SeqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final SeqRepository seqRepository;

    @Transactional
    public Page<Answer> findAnswers(Long serveyId, Long questionId, Pageable pageable){
        Page<Answer> answers = answerRepository.findBySurveyIdAndQuestionId(serveyId, questionId, pageable);
        return answers;
    }


    @Transactional
    public long answers(List<Answer> answers){
        Long targetSeq = seqRepository.findSeq("target");

        LocalDateTime date = LocalDateTime.now();
        for( Answer answer  : answers){
            answer.setTargetSeq(targetSeq);
            answer.setDate(date);
        }

        answerRepository.saveAll(answers);
        return answers.get(0).getSurveyId();
    }
}

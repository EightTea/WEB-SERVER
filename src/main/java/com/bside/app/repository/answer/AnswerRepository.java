package com.bside.app.repository.answer;

import com.bside.app.domain.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Answer> findAll (Integer surveyId, Integer questionId){
        return em.createQuery("select a from Answer a where a.survey_id = :survey_id and a.question_id = :question_id", Answer.class)
                .setParameter("servey_id", surveyId)
                .setParameter("question_id", questionId)
                .getResultList();
    }
}

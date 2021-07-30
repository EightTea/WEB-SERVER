package com.bside.app.repository.survey;

import com.bside.app.domain.Survey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class SurveyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Survey survey){
        em.persist(survey);
    }

    public List<Survey> findAll(Long userId){
        List<Survey> surveyList = em.createQuery("select s from Survey s where s.userId = :userId", Survey.class)
        .setParameter("userId", userId)
        .getResultList();

        return surveyList;
    }

    public Optional<Survey> findQrUrlById(Long userId){
        Survey survey = em.find(Survey.class, userId);
        return Optional.ofNullable(survey);
    }
}

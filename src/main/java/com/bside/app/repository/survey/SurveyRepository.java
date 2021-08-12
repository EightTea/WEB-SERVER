package com.bside.app.repository.survey;

import com.bside.app.domain.Survey;
import com.bside.app.domain.SurveyStatus;
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

    public List<Survey> findAllByUserId(Long userId){
        List<Survey> surveyList = em.createQuery("select s from Survey s where s.userId = :userId", Survey.class)
        .setParameter("userId", userId)
        .getResultList();

        return surveyList;
    }

    public Optional<Survey> findQrUrlById(Long userId){
        Survey survey = em.find(Survey.class, userId);
        return Optional.ofNullable(survey);
    }

    public Optional<Survey> findById(Long surveyId){
        Survey survey = em.find(Survey.class, surveyId);
        return Optional.ofNullable(survey);
    }

    public Integer updateStatusById(Long surveyId, SurveyStatus status){
        System.out.println(surveyId + " " + status);
        int result = em.createQuery("update Survey s set s.status= :status where s.id= :surveyId")
                .setParameter("status", status)
                .setParameter("surveyId", surveyId).executeUpdate();
        return result;
    }

    public Optional<Survey> existsByIdAndUserId(Long surveyId, Long userId){
        Optional<Survey> result = em.createQuery("select s from Survey s where s.id = :id and s.userId = :userId", Survey.class)
                .setParameter("id", surveyId)
                .setParameter("userId", userId)
                .getResultList().stream().findAny();
        return result;
    }
}

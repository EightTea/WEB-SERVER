package com.bside.app.repository.survey;

import com.bside.app.domain.Survey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SurveyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Survey survey){
        em.persist(survey);
    }

}

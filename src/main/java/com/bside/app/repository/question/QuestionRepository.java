package com.bside.app.repository.question;

import com.bside.app.domain.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Question question){
        em.persist(question);
    }
}

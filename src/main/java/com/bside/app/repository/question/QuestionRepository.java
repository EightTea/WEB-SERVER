package com.bside.app.repository.question;

import com.bside.app.domain.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Question question){
        em.persist(question);
    }

    public List<Question> findAll(Long surverId){
        List<Question> questionList = em.createQuery("select q from Question q where q.servey_id = :survey_id", Question.class)
                .setParameter("survey_id", surverId)
                .getResultList();

        return questionList;
    }


}

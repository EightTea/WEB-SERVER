package com.bside.app.repository.question;

import com.bside.app.domain.Question;
import com.bside.app.domain.Survey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Question question){
        em.persist(question);
    }

    public List<Question> findAll(Long surverId){
        List<Question> questionList = em.createQuery("select q from Question q where q.survey.id = 2", Question.class)
                .getResultList();
        return questionList;
    }


}

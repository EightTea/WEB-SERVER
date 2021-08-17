package com.bside.app.repository.answer;

import com.bside.app.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query("select a from Answer a where a.surveyId = ?1 and a.questionId = ?2")
    Page<Answer> findBySurveyIdAndQuestionId(Long surveyId, Long questionId, Pageable pageable);

    @Query("select a.targetSeq from Answer a where a.surveyId = ?1 group by a.targetSeq")
    List<Long> countBySurveyId(Long surveyId);
}
/*
    public Page<Answer> findAll (Integer surveyId, Integer questionId){
        List<Answer> result = em.createQuery("select a from Answer a where a.survey_id = :survey_id and a.question_id = :question_id", Answer.class)
                .setParameter("servey_id", surveyId)
                .setParameter("question_id", questionId)
                .getResultList();
        Page<Answer> page = new PageImpl<>(result);
        return page;
    }
 */

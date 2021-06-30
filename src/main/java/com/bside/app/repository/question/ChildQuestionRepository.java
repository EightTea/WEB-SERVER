package com.bside.app.repository.question;

import com.bside.app.domain.ChildQuestion;
import com.bside.app.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChildQuestionRepository {

    ChildQuestion save(ChildQuestion cq);
    Optional<ChildQuestion> findById(Long id);
    Optional<ChildQuestion> findByParentQuestionId(Long pq_id);
    Optional<ChildQuestion> findByQuestionNum(Integer question_num);
    List<ChildQuestion> findAll();
}

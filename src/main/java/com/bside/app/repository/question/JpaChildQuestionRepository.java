package com.bside.app.repository.question;

import com.bside.app.domain.ChildQuestion;

import java.util.List;
import java.util.Optional;

public class JpaChildQuestionRepository implements ChildQuestionRepository {

    @Override
    public ChildQuestion save(ChildQuestion cq) {
        return null;
    }

    @Override
    public Optional<ChildQuestion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ChildQuestion> findByParentQuestionId(Long pq_id) {
        return Optional.empty();
    }

    @Override
    public Optional<ChildQuestion> findByQuestionNum(Integer question_num) {
        return Optional.empty();
    }

    @Override
    public List<ChildQuestion> findAll() {
        return null;
    }
}

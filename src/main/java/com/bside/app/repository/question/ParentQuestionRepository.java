package com.bside.app.repository.question;

import com.bside.app.domain.ParentQuestion;
import com.bside.app.domain.User;

import java.util.List;
import java.util.Optional;

public interface ParentQuestionRepository {

    ParentQuestion save(ParentQuestion pq);
    Optional<ParentQuestion> findById(Long id);
    Optional<ParentQuestion> findByUsersId(Long users_id);
    List<ParentQuestion> findAll();
}

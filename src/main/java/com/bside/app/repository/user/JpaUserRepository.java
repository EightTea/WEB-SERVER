package com.bside.app.repository.user;

import com.bside.app.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User findUser = em.find(User.class, id);
        return Optional.ofNullable(findUser);
    }

    @Override
    public List<User> findAll() {

        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public User update(User newUser){
        User findOne = em.find(User.class, newUser.getId());
        findOne.setStore_name(newUser.getStore_name());
        findOne.setEmail(newUser.getEmail());
        findOne.setNickName(newUser.getNickName());
        findOne.setGender(newUser.getGender());
        findOne.setYear(newUser.getYear());
        findOne.setStatus(1);

        return findOne;
    }

    @Override
    public User setStatusById(Long id) {
        User findUser = em.find(User.class, id);
        findUser.setStatus(0);
        return findUser;
    }

    @Override
    public void deleteById(Long id){
        User findUser = em.find(User.class, id);
        em.remove(findUser);
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}

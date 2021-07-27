package com.bside.app.repository.user;

import com.bside.app.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

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

    public Optional<User> findByIdAndStatus(Long id, Integer status){
        List<User> result = em.createQuery("select u from User u where u.id = ?1 and u.status = ?1", User.class)
                .getResultList();
        return result.stream().findAny();
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
        findOne.setNick_name(newUser.getNick_name());
        findOne.setGender(newUser.getGender());
        findOne.setYear(newUser.getYear());
        findOne.setStatus(1);

        return findOne;
    }

    @Override
    public void deleteById(User user) {
        user.setStatus(0);
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}

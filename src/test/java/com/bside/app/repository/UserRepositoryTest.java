package com.bside.app.repository;

import com.bside.app.domain.Authority;
import com.bside.app.domain.User;
import com.bside.app.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired UserRepository userRepository;



    @Test
    void save(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        User saveUser = userRepository.save(user);

        // then
        assertThat(saveUser).isEqualTo(user);
    }

    @Test
    void findById(){
        // given
        User user = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    void findAll(){
//
//        // given
//        User[] user = new User[3];
//        for(int i=0;i<3;i++){
//            user[i] = new User((long) i, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);
//        }
//
//        // when
//
//        // 원래 DB에 있던 user 수
//        long originListSize = userRepository.findAll().size();
//
//        for(int i=0;i<3;i++){
//            userRepository.save(user[i]);
//        }
//
//        long updateListSize = userRepository.findAll().size();
//
//        // then
//        assertThat(updateListSize).isEqualTo(originListSize + 3);
    }

    @Test
    void update(){
        // given
        User originUser = new User(1L, "원래 가게 이름", 0, "닉네임1", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);
        User updateUser = new User(1L, "바뀐 가게 이름", 1, "닉네임2", "bbb@gmail.com", 0, "1999", Authority.ROLE_USER);

        Long originId = originUser.getId();
        String originNickName = originUser.getNickName();
        Integer originStatus = originUser.getStatus();

        // when
        userRepository.save(originUser);
        userRepository.update(updateUser);

        // then
        assertThat(originId).isEqualTo(updateUser.getId());
        assertThat(originNickName).isNotEqualTo(updateUser.getNickName());
        assertThat(originStatus).isNotEqualTo(updateUser.getStatus());
    }

    @Test
    void setStatusById(){
        // given
        User deletedUser = new User(1L, "가게 이름", 1, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userRepository.save(deletedUser);
        userRepository.setStatusById(deletedUser.getId());

        // then
        assertThat(deletedUser.getStatus()).isEqualTo(0);
    }

    @Test
    void deleteById(){
        // given
        User user = new User(1L, "가게 이름", 0, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        Optional<User> findUser = userRepository.findById(user.getId());

        // then
        assertThat(findUser.isEmpty()).isEqualTo(true);
    }

    @Test
    void existsById(){
        // given
        User user = new User(1L, "가게 이름", 0, "닉네임", "aaa@gmail.com", 1, "2000", Authority.ROLE_USER);

        // when
        userRepository.save(user);
        boolean exists = userRepository.existsById(user.getId());

        // then
        assertThat(exists).isEqualTo(true);
    }
}

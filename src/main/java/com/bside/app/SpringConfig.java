package com.bside.app;

import com.bside.app.repository.user.UserRepository;
import com.bside.app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;

    public SpringConfig(EntityManager em, UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }
}

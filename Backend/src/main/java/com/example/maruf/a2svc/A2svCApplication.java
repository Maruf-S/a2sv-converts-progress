package com.example.maruf.a2svc;

//import com.example.maruf.a2svc.configs.MvcConfig;
import com.example.maruf.a2svc.models.User;
import com.example.maruf.a2svc.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class A2svCApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(A2svCApplication.class, args);
        UserRepository userRepository = configurableApplicationContext.getBean(UserRepository.class);
    }
}

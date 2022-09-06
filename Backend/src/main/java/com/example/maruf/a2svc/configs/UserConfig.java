package com.example.maruf.a2svc.configs;

import com.example.maruf.a2svc.repository.LeetCodeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableScheduling
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(LeetCodeRepository leetCodeRepository) {
        return args -> {
//            System.out.println(getTheDamnStat("Maruf-s").data.matchedUser.username);
            leetCodeRepository.refreshUsers();
        };
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
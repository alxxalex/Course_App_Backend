package com.ase.licenta.course_app.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/users","/api/courses/delete/{id}","/api/enrollment/add","/api/courses/rating","/api/courses/update","/api/courses/add","/api/videos","/api/courses/thumbnail")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(withDefaults()));

        http.cors(withDefaults());

//        http.setSharedObject(ContentNegotiationStrategy.class, new
//                HeaderContentNegotiationStrategy());
//
//
//        Okta.configureResourceServer401ResponseBody(http);

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}

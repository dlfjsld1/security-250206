package com.example.securityExam.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/h2-console/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/*/posts/{id:\\d+}", "/api/*/posts", "/api/*/posts/{postId:\\d+}/comments")
                                .permitAll() //이 위에 지정한건 허용하고
                                .anyRequest()
                                .authenticated() // 나머지는 막아라
                )
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // csrf 끄기. api 서버는 csrf 끄는게 일반적
                .csrf((csrf) -> csrf.disable())
                //기본 필터 전 후에 addFilterBefore addFilterAfter로 붙일 수 있음
                //UsernamePasswordAuthenticationFilter는 기본 필터.
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
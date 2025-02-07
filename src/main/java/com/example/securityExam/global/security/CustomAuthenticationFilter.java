package com.example.securityExam.global.security;

import com.example.securityExam.global.Rq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final Rq rq;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        rq.setLogin("user1"); // user1이 로그인했다.

        //doFilter의 역할은 다음으로 넘어가라는 것. 다음은 다음 필터가 될수도, 그냥 넘어가는 걸수도 있음
        filterChain.doFilter(request, response);
    }
}

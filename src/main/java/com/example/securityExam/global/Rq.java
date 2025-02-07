package com.example.securityExam.global;

import com.example.securityExam.domain.member.member.entity.Member;
import com.example.securityExam.domain.member.member.service.MemberService;
import com.example.securityExam.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

// Request, Response, Session, Cookie, Header
@Component
@RequiredArgsConstructor
//매 요청마다 생성되고 사라지게 하는 어노테이션
@RequestScope
public class Rq {

    private final HttpServletRequest request;
    private final MemberService memberService;

    public Member getAuthenticatedActor() {

        String authorizationValue = request.getHeader("Authorization");
        String apiKey = authorizationValue.substring("Bearer ".length());
        Optional<Member> opActor = memberService.findByApiKey(apiKey);

        if(opActor.isEmpty()) {
            throw new ServiceException("401-1", "잘못된 인증키입니다.");
        }

        return opActor.get();
    }

    public void setLogin(String username) {
        //유저 정보 생성
        UserDetails user = new User(username, "", List.of());

        //인증 정보 저장소. security는 여기를 확인해 해당 유저가 존재하면 로그인 한 것으로 인식.
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }
}

package edu.du.sb1022secu4_2.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 Spring의 설정 클래스를 나타냄
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메서드 수준의 보안 활성화 (예: @PreAuthorize)
@Log4j2 // 로그 기록을 위한 Log4j2 사용
public class SecurityConfig {

// service.UserDetailsServiceImpl을 만들어서 주석처리함. passwordEncoder는 있어야함.

    @Bean // line31. PasswordEncoder Bean 등록
    PasswordEncoder passwordEncoder() {
        // BCrypt 알고리즘을 사용하여 비밀번호를 암호화하는 PasswordEncoder 생성
        return new BCryptPasswordEncoder();
    }
//    @Bean // UserDetailsService Bean 등록
//    public UserDetailsService userDetailsService() { // [인증 절차]
//        // 사용자 정보를 담은 UserDetails 객체 생성
//        UserDetails user = User.withUsername("user") // 사용자 이름 설정
//                .password(passwordEncoder().encode("1234")) // 비밀번호 암호화
//                .roles("ADMIN").roles("USER") // 역할 설정
//                // admin 접속 불가, 마지막 순서 "USER" Role이 적용된 것.
//                .build(); // UserDetails 객체 생성
//
//        // 메모리에 사용자 정보를 저장하는 InMemoryUserDetailsManager 반환
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean // SecurityFilterChain Bean 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // [인가 절차]
        log.info("---------FilterChain---------"); // 필터 체인이 생성될 때 로그 기록

        // 인가(Authorization) 설정
        http.authorizeHttpRequests() // HTTP 요청에 대한 인가 설정
//                .antMatchers("/**").permitAll() //  "/**" 모든 요청을 허용
//                .antMatchers("/h2-console/**").permitAll() // permitAll()을 해도 접근거부
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/admin").hasRole("ADMIN") // hasRole을 사용해 Role이 ADMIN인 경우만 접속 가능하게 함. 아니면 denyAll()
//                .antMatchers("/**").denyAll() // "/**" 모든 요청을 차단
                .anyRequest().authenticated(); // 그 외의 요청은 인증 필요

        http.formLogin(); // 기본 로그인 폼 사용 설정

        http.csrf().disable();
        http.logout();

        // denyAll()이지만 예외요청이 들어오면 view를 보여줌
        http.exceptionHandling().accessDeniedPage("/sample/accessDenied");
        // "/sample/accessDenied" 요청명

        http.csrf() // Security에서 h2-DB 사용하기 위한 코드
                .ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();

        return http.build(); // 설정한 보안 필터 체인 반환
    }

}


package edu.du.sb1022secu2.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable이 붙으면 자동화 기능 Security 관련 자동화
@Log4j2 // 로그 기록
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("---------FilterChain---------");
        http.authorizeHttpRequests() // 인가(Authorization), 예외처리 필요
//                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin(); // 기본 로그인 폼
        return http.build();
    }
}

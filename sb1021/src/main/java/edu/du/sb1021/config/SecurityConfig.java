package edu.du.sb1021.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // FilterChain: dispatch 에 들어가기 전에 거르는 필터.

    /*
    (1) return http.build(); // 코드가 리턴문만 있을 구동 시 로그인없이 바로 /sample/all 화면이 나옴.

    (2) http.authorizeHttpRequests()
                .antMatchers("/sample/all").permitAll()
                .anyRequest().authenticated();
        return http.build(); // localhost:8887에 연결(접근)을 거부.
    }
    (3) */
        http.authorizeHttpRequests() // HTTP 요청에 대한 권한 설정을 시작
                .antMatchers("/css/**").permitAll() // "/" 경로에 대한 요청은 모든 사용자에게 허용
                .antMatchers("/js/**").permitAll()  // "/"는 static 부트스트랩도 적용되지 않기 때문에
                .antMatchers("/assets/**").permitAll() // static 경로도 허용하도록 코드 추가
                .anyRequest().authenticated(); // 그 외의 모든 요청은 인증된 사용자(로그인)만 접근할 수 있도록 설정
        http.formLogin(); // HttpSecurity 객체에 대해 폼 로그인을 구성.

        return http.build(); // HttpSecurity 객체의 구성 완료 후, 최종 빌드.
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // passwordEncoder()
    }

    @Bean
    public UserDetailsService userDetailsService() { // 백도어 코드라고 생각.
        UserDetails user = User.withUsername("user1")
                .password(passwordEncoder().encode("1234")) // passwordEncoder().encode 암호화
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
        // 아이디, 비밀번호의 기본값을 지정함. 구동 시 콘솔창에 나오던 암호화된 패스워드가 안 나옴.
    }
}

// 필터와 인터셉터 차이, 세션
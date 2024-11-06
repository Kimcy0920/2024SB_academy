package edu.du.sb1022secu4_2.service;

import edu.du.sb1022secu4_2.entity.Member;
import edu.du.sb1022secu4_2.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("===========> USERNAME: " + username);
//        UserDetails user = User.withUsername("user123") // 사용자 이름 설정
//                .password(passwordEncoder().encode("1234")) // 비밀번호 암호화
//                .roles("ADMIN") // 역할 설정
//                .build();
//        return user;

//        Member member = Member.builder()
//                .id(1001L)
//                .username("홍길동")
//                .password(passwordEncoder().encode("1234"))
//                .email("hong@korea.com")
//                .build();
//        return toUserDetails(member);
        Optional<Member> member = memberRepository.findByUsername(username);
        return toUserDetails(member.get());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails toUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
//                .authorities(new SimpleGrantedAuthority(member.getRole().toString()) // Member 클래스에 Roles를 만들지 않음
                .roles("USER") // 강제로 USER_ROLE를 부여함.
                .build();
    }
}

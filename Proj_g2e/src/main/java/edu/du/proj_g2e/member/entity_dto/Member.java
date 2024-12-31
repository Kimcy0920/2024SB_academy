package edu.du.proj_g2e.member.entity_dto;

import edu.du.proj_g2e.member.exception.WrongIdPasswordException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime regDate;

    // 약관 동의
    private boolean agreeTerms;
    private boolean agreePrivacy;

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }

    @PrePersist
    public void prePersist() { // 현재 시간을 자동으로 설정
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now();
        }
    }
}

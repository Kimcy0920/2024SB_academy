package edu.du.sb1024.entity;

import edu.du.sb1024.survey.AnsweredData;
import edu.du.sb1024.survey.Respondent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data            // Getter Setter
@Builder        // DTO -> Entity화
@AllArgsConstructor    // 모든 컬럼 생성자 생성
@NoArgsConstructor    // 기본 생성자
@Table(name = "member")
public class Member {

    @Id    // 내가 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)	// 자동 id 생성
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private LocalDateTime registerDateTime;

//    @OneToOne
//    @JoinColumn(name = "ANSWERED_ID")
//    private AnsweredData ans;
//
//    @OneToOne
//    @JoinColumn(name = "RESPONDENT_ID")
//    private Respondent res;

    public Member(String email, String password,
                  String username, String role, LocalDateTime regDateTime) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.registerDateTime = regDateTime;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }
}
package edu.du.sb1101.registerMember.entity;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.point.PointLog;
import edu.du.sb1101.registerMember.spring.WrongIdPasswordException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String address;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private LocalDateTime regdate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private int point;

    // 추가: PointLog와의 관계 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PointLog> pointLogs = new ArrayList<>();

    private LocalDate lastPointDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Title> titles = new ArrayList<>();  // 회원이 가진 칭호들

    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword;
    }
}

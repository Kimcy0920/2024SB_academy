package edu.du.sb1023.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;

    private String username;

    @ToString.Exclude
    @ManyToOne // 다대일 관계
    @JoinColumn(name = "team_id") // 조인컬럼명 지정, 생략가능하나 team_team_id로 나옴
    private Team team; // 팀을 만들어서 멤버 넣기

    public Member(String id, String username) {
        this.id = id;
        this.username = username; // 테스트하기 위해 생성자 만들기
    }
}
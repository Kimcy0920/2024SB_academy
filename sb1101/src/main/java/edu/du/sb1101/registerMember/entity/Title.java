package edu.du.sb1101.registerMember.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // 칭호명 (예: '공로자', '우수회원' 등)

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 해당 칭호를 가진 회원

    private LocalDateTime regDate;  // 칭호가 적용된 날짜

    private boolean active = false;  // 칭호가 활성화된 상태인지 여부
}


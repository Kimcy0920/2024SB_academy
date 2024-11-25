package edu.du.sb1101.point;


import edu.du.sb1101.registerMember.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class PointLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String actionType; // e.g., "EARNED", "USED"

    @Column(nullable = false)
    private int points;

    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
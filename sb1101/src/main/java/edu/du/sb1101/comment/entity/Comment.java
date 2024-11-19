package edu.du.sb1101.comment.entity;

import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.registerMember.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CommentId;

    @Size(max = 200, message = "댓글은 200자 이내로 작성해주세요.")
    @Column(nullable = false)
    private String content;

    private LocalDateTime commentDate;

    private String username;

    @ManyToOne
    @JoinColumn(name = "boardIdx")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}

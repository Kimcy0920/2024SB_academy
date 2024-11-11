package edu.du.sb1101.comment.entity;

import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.registerMember.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

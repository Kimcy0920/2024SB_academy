package edu.du.sb1011_th.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

// 기존 sb1011에서 BoardDto -> entity.board

@Entity
@Table(name = "t_board")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardIdx;

    private String title;

    private String contents;

    private int hitCnt;

    private String creatorId;

    private Date createdDatetime;

    private String updaterId;

    private Date updatedDatetime;

    private String deletedYn; // 컬럼 추가
}

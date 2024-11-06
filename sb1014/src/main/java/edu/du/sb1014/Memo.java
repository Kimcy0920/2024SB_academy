package edu.du.sb1014;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Builder // Builder 사용하려면 생성자 필요
@AllArgsConstructor // 매개변수 생성자
@NoArgsConstructor // 빈 생성자
@ToString
@Entity
@Table(name = "tbl_memo") // 테이블명 변경
public class Memo { // 기본키 필요

    @Id // 기본키 설정, id가 없으면 오류 발생함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 지정
    private long id;

    @Column(length = 100, nullable = false) // 컬럼 속성 변경
    private String memoText; // mariaDB -> memo_text
}

/*
    == 코드 구동 시 콘솔창 결과 ==
    create table memo (
       id bigint not null,
        memo_text varchar(255),
        primary key (id)
    ) engine=InnoDB

    --> MariaDB-root-meme 테이블이 생성됨.
*/
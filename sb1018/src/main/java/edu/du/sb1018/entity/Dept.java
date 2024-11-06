package edu.du.sb1018.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

// 테이블이 있는데 Entity를 사용하는 이유는 객체지향으로 DB를 사용하기 위함.
@Entity
@ToString
@Getter
@Setter
public class Dept { // 1교시

    @Id
    private Integer deptno;
    private String dname;
    private String loc;
}

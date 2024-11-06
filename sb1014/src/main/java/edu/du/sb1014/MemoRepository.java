package edu.du.sb1014;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> { // JPA 상속
                                     // JpaRepository<entity 명, primary key 타입>

    // === 쿼리 메소드 ===

    List<Memo> findByIdBetweenOrderByIdDesc(Long min, Long max);
    // select * from memo where id between 2 and 7;

    List<Memo> findByIdBetween(Long min, Long max);
    // select * from memo where id between 2 and 7 order by id desc;

    // === 쿼리 어노테이션 ===

    // @Query("select m [from 테이블명이 아닌 Entity 명인 Memo] m order by m.id desc")
    @Query("select m from Memo m order by m.id desc")
    List<Memo> getListDesc();

    @Query("select count(m) from Memo m")
    int getCount();

    @Query("select m from Memo m where m.id = :id")
    Memo getById(Long id);
}

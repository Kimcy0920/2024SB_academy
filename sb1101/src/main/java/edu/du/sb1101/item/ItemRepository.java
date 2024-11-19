package edu.du.sb1101.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findAll(Pageable pageable); // 공지사항 최근등록순

    List<Item> findTop5ByOrderByItemIdDesc();

    Page<Item> findByItemNameContaining(String itemName, Pageable pageable);
}

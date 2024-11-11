package edu.du.sb1101.comment.repository;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBoard(Board board);
}

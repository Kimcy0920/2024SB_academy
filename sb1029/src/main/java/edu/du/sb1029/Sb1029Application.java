package edu.du.sb1029;

import edu.du.sb1029.board.entity.Board;
import edu.du.sb1029.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb1029Application {

    @Autowired
    BoardRepository boardRepository;

    public static void main(String[] args) {
        SpringApplication.run(Sb1029Application.class, args);
    }
    @PostConstruct
    public void init() {

        IntStream.rangeClosed(1, 30).forEach(i->{
            Board board = Board.builder()
                    .title("제목"+i)
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                    .contents("내용"+i)
                    .deletedYn("N")
                    .hitCnt(0)
                    .build();
            boardRepository.save(board);
        });

    }
}

package edu.du.kcy1101;

import edu.du.kcy1101.board.entity.Board;
import edu.du.kcy1101.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Kcy1101Application {

    public static void main(String[] args) {
        SpringApplication.run(Kcy1101Application.class, args);
    }

    @Autowired
    BoardRepository boardRepository;

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

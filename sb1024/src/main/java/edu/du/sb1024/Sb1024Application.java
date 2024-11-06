package edu.du.sb1024;

import edu.du.sb1024.fileuploadboard.entity.Board;
import edu.du.sb1024.fileuploadboard.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb1024Application {



    public static void main(String[] args) {
        SpringApplication.run(Sb1024Application.class, args);

    }

    @Autowired
    BoardRepository boardRepository;

    @PostConstruct
    public void init() { // 샘플 게시글 생성코드
        IntStream.rangeClosed(1, 50).forEach(i-> {
        // 1~50까지 반복 stream(람다식) i에 1부터 50까지 순서대로 값 넣기
            Board board = Board.builder()
                    .title("Sample Title " + i)
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                    .contents("Sample Contents " + i)
                    .deletedYn("N")
                    .hitCnt(0)
                    .build();
            boardRepository.save(board);
        });
    }
}

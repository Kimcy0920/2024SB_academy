package edu.du.sb1101;

import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.notice.entity.Notice;
import edu.du.sb1101.notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb1101Application {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    NoticeRepository noticeRepository;

    public static void main(String[] args) {
        SpringApplication.run(Sb1101Application.class, args);
    }

    @PostConstruct
    public void init() {

        IntStream.rangeClosed(1, 50).forEach(i->{
            Board board = Board.builder()
                    .title("게시글 제목"+i)
                    .contents("게시글 내용"+i)
                    .creatorId("사용자")
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 18))
                    .hitCnt(0)
                    .deletedYn("N")
                    .build();
            boardRepository.save(board);
        });

        IntStream.rangeClosed(1, 50).forEach(i->{
            Notice notice = Notice.builder()
                    .title("공지사항 제목"+i)
                    .content("공지사항 내용"+i)
                    .username("관리자")
                    .regdate(LocalDateTime.now())
                    .build();
            noticeRepository.save(notice);
        });

    }

}

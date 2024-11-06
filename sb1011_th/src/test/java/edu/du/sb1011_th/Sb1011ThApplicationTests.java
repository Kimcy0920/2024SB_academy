package edu.du.sb1011_th;

import edu.du.sb1011_th.entity.Board;
import edu.du.sb1011_th.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sb1011ThApplicationTests {

	@Autowired
	BoardRepository boardRepository;

	@Test
	void selectAll() {
		for (Board board : boardRepository.findAll()) {
			System.out.println(board);
		}
	}

//	@Test
//	void selectAll2() {
//		for (Board board : boardRepository.selectBoardList()) {
//			System.out.println(board);
//		}
//	}


}

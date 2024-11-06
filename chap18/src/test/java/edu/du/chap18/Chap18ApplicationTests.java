package edu.du.chap18;

import edu.du.chap18.dao.ArticleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Chap18ApplicationTests {

	@Autowired
	ArticleDao articleDao;

	@Test
	void 게시판카운트() {
		System.out.println(articleDao.selectCount());

	}

}

package edu.du.chap18.service;

import edu.du.chap18.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DeleteArticleService {

	@Autowired
	ArticleDao articleDao;

	private DeleteArticleService() {
	}

	public void deleteArticle(DeleteRequest deleteRequest)
			throws ArticleNotFoundException, InvalidPasswordException {

			ArticleCheckHelper checkHelper = new ArticleCheckHelper();
//			checkHelper.checkExistsAndPassword(conn, deleteRequest
//					.getArticleId(), deleteRequest.getPassword());

			articleDao.delete(deleteRequest.getArticleId());

	}
}

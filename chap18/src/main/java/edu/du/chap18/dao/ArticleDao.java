package edu.du.chap18.dao;

import edu.du.chap18.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ArticleDao {

    @Select("select count(*) from article")
    public int selectCount();

    @Select("select article_id id, group_id groupId, sequence_no sequenceNumber, posting_date postingDate, read_count readCount, writer_name writerName, password, title "
            + "from article order by sequence_no desc limit #{firstRow}, #{endRow}")
    public List<Article> select(int firstRow, int endRow);

    @Select("select * from article where article_id = #{id}")
    public Article selectById(int articleId);

    @Insert("insert into article "
            + "(group_id, sequence_no, posting_date, read_count, writer_name, password, title, content)"
            + "values (#{group_id}, #{sequence_no}, now(), 0, #{writer_name}, #{password}, #{title}, #{content})")
    public int insert(Article article);

    public void increaseReadCount(int articleId);
    public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum);
    public int update(Article article);
    public void delete(int articleId);
}

package edu.du.sb1008.dao;

import edu.du.sb1008.dto.SimpleBbsDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ISimpleBbsDao {

//    @Select("select * from simple_bbs order by id desc")
    // xml 파일없이 할 경우 위 코드로도 가능
    public List<SimpleBbsDto> listDao();

//    @Select("select * from simple_bbs where id = #{id}")
//    public SimpleBbsDto viewDao(@Param("id") String id);
    public SimpleBbsDto viewDao(String id);

//    @Insert("insert into simple_bbs(writer, title, content values(#{writer}, #{title}, #{content})")
    public int writeDao(String writer, String title, String content);

//    @Delete("delete from simple_bbs where id = #{id}")
    public int deleteDao(String id);

//    @Update("update simple_bbs set writer = #{writer}, title = #{title}, content = #{content} where id = #{id}")
    public int updateDao(String writer, String title, String content, String id);
}

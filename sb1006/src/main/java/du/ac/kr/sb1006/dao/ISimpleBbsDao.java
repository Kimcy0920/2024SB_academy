package du.ac.kr.sb1006.dao;

import du.ac.kr.sb1006.dto.SimpleBbsDto;

import java.util.List;

public interface ISimpleBbsDao {
    public List<SimpleBbsDto> listDao();
    public SimpleBbsDto viewDao(String id);
    public int writeDao(String writer, String title, String content);
    public int deleteDao(String id);
    public int updateDao(String id, String writer, String title, String content);
}

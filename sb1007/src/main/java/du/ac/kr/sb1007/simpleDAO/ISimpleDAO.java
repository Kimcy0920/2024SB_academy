package du.ac.kr.sb1007.simpleDAO;

import du.ac.kr.sb1007.simpleDTO.SimpleDTO;

import java.util.List;

public interface ISimpleDAO {
    public List<SimpleDTO> selectDAO();
    public SimpleDTO selectOneDAO(String id);
    public int insertDAO(String writer, String title, String content);
    public int updateDAO(String writer, String title, String content, String id);
    public int deleteDAO(String id);
}

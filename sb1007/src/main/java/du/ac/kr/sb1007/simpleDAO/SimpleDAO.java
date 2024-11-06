package du.ac.kr.sb1007.simpleDAO;

import du.ac.kr.sb1007.simpleDTO.SimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleDAO implements ISimpleDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<SimpleDTO> selectDAO() {
        String sql = "select * from simple_bbs order by id desc";
        List<SimpleDTO> listDTO = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<SimpleDTO>(SimpleDTO.class));
        return listDTO;
    }

    @Override
    public SimpleDTO selectOneDAO(String id) {
        String sql = "select * from simple_bbs where id = " + id;
        SimpleDTO dto = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<SimpleDTO>(SimpleDTO.class));
        return dto;
    }

    @Override
    public int insertDAO(String writer, String title, String content) {
        String sql = "insert into simple_bbs(writer, title, content) values(?, ?, ?)";
        return jdbcTemplate.update(sql, writer, title, content);
    }

    @Override
    public int updateDAO(String writer, String title, String content, String id) {
        String sql = "update simple_bbs set writer = ?, title = ?, content = ? where id = ?";
        return jdbcTemplate.update(sql, writer, title, content, Integer.parseInt(id));
    }

    @Override
    public int deleteDAO(String id) {
        String sql = "delete from simple_bbs where id = ?";
        return jdbcTemplate.update(sql, Integer.parseInt(id));
    }
}

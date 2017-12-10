package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.TitleDTO;

@Table(pojo =TitleDTO.class ,table = "m_title",pk = "t_id")
public class TitleDAO extends BaseDAO{
}

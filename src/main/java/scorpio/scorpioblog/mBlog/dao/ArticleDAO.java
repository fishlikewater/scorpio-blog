package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticlesDTO;

@Table(pojo = ArticlesDTO.class,table = "m_article", pk = "a_id")
@Component
public class ArticleDAO extends BaseDAO {
}

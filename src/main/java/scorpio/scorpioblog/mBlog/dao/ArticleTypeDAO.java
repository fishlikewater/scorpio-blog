package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleTypeDTO;

@Table(pojo = ArticleTypeDTO.class,table = "article_type")
@Component
public class ArticleTypeDAO extends BaseDAO{
}

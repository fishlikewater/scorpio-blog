package scorpio.scorpioblog.mBlog.dao;

import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableRelationDTO;

@Table(pojo = ArticleLableRelationDTO.class, table = "m_article_lable")
public class ArticleLableRelationDAO extends BaseDAO{
}

package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleDetailDTO;

@Table(pojo = ArticleDetailDTO.class,table = "m_detail")
public class ArticleDetailDAO extends BaseDAO{
}

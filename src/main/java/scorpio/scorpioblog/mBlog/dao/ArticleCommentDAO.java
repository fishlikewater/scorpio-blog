package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleCommentDTO;

@Table(pojo = ArticleCommentDTO.class,table = "article_comment")
@Component
public class ArticleCommentDAO extends BaseDAO{

}

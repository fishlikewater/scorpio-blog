package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.scorpioblog.mBlog.dto.ArticleCommentDTO;

@Table(pojo = ArticleCommentDTO.class,table = "")
@Component
public class ArticleCommentDAO {
}

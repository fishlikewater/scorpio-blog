package scorpio.scorpioblog.mBlog.dao;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableDTO;

@Table(pojo = ArticleLableDTO.class,table = "article_lable")
public class ArticleLableDAO extends BaseDAO{
}

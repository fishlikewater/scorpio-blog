package scorpio.scorpioblog.mBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.scorpioblog.mBlog.dao.ArticleCommentDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleCommentDTO;
import scorpio.scorpioblog.utils.DateUtil;

import java.util.Date;

@Service
public class ArticleCommentService {

    @Autowired
    private ArticleCommentDAO articleCommentDAO;

    /**
     * 添加评论
     * @param dto
     */
    public void edit(ArticleCommentDTO dto){
        dto.setTime(DateUtil.formatDate(new Date()));
        articleCommentDAO.createAndId(dto);
    }

    /**
     * 删除评论
     * @param id
     */
    public void remove(String id){
        articleCommentDAO.remove(id);
    }
}

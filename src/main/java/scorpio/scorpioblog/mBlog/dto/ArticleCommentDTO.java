package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.core.BasicObject;

/**
 * 评论
 */
@Data
public class ArticleCommentDTO extends BasicObject{

    private String id;

    private String aId;

    private String content;

    private String time;

    private String email;
}

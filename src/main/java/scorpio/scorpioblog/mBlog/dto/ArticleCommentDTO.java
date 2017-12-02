package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.core.BaseObject;

/**
 * 评论
 */
@Data
public class ArticleCommentDTO extends BaseObject{

    private String id;

    private String aId;

    private String content;

    private String time;

    private String email;
}

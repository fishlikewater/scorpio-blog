package scorpio.scorpioblog.mBlog.dto;


import lombok.Data;
import scorpio.core.BaseObject;

@Data
public class ArticleLableRelationDTO extends BaseObject{

    private Integer id;

    private String articleId;

    private Integer lableId;
}

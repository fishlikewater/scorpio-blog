package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.core.BasicObject;

@Data
public class ArticleTypeDTO extends BasicObject{

    private String id;

    private String name;

    private String orderBy;
}

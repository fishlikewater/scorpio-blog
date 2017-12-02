package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.core.BaseObject;
@Data
public class ArticleTypeDTO extends BaseObject {

    private String id;

    private String name;

    private String typeIcon;

    private String orderBy;
}

package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseObject;

@Data
public class ArticleDetailDTO extends BaseObject {

    private String id;

    private String content;
}

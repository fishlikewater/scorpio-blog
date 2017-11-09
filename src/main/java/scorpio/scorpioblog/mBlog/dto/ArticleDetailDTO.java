package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseObject;

@Data
@Component
@Table(pojo = ArticleDetailDTO.class,table = "m_detail")
public class ArticleDetailDTO extends BaseObject {

    private String id;

    private String content;
}

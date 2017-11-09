package scorpio.scorpioblog.mBlog.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import scorpio.scorpioblog.mBlog.dto.ArticleDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleDetailDTO;
import scorpio.utils.UUIDUtils;

@Service
public class ArticleService {

    /**
     * 编辑文章
     * @param dto
     */
    public void edit(ArticleDTO dto, String content){
        if(StringUtils.isBlank(dto.getaId())){
            dto.setaId(UUIDUtils.get());
        }
        dto.save();
        ArticleDetailDTO ddto = new ArticleDetailDTO();
        ddto.setId(dto.getaId());
        ddto.setContent(content);
        ddto.save();
    }
}

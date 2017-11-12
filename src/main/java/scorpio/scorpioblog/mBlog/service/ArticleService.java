package scorpio.scorpioblog.mBlog.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import scorpio.BaseUtils;
import scorpio.scorpioblog.mBlog.BlogConstant;
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
            ArticleDetailDTO ddto = new ArticleDetailDTO();
            ddto.setId(UUIDUtils.get());
            ddto.setContent(content);
            ddto.save();
            dto.setaId(UUIDUtils.get());
            dto.setContentId(ddto.getId());
            String shortComment = "";
            if(content.length()>100){
                shortComment = content.substring(0, 100);
            }else{
                shortComment = content;
            }
            dto.settPic(BlogConstant.getPic());
            dto.setShortContent(shortComment+"......");
            dto.save();
        }else {
            ArticleDetailDTO ddto = new ArticleDetailDTO();
            ddto.setId(dto.getContentId());
            ddto.setContent(content);
            ddto.update();
            String shortComment = "";
            if(content.length()>100){
                shortComment = content.substring(0, 100);
            }else{
                shortComment = content;
            }
            dto.setShortContent(shortComment+"......");
            dto.update();
        }
    }
}

package scorpio.scorpioblog.mBlog.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.annotation.Tranctional;
import scorpio.scorpioblog.mBlog.dao.ArticleLableDAO;
import scorpio.scorpioblog.mBlog.dao.ArticleLableRelationDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableRelationDTO;

import java.sql.Connection;
import java.util.List;

@Service
@Slf4j
public class ArticleLableService {

    @Autowired
    private ArticleLableDAO articleLableDAO;
    @Autowired
    private ArticleLableRelationDAO articleLableRelationDAO;

    /**
     * 标签编辑
     * @param dto
     */
    public Integer edit(ArticleLableDTO dto){

        Integer id = null;
        if(dto.getId() == null){
            id = (Integer) articleLableDAO.create(dto);
        }else{
            id = dto.getId();
            articleLableDAO.update(dto);
        }

        return id;
    }

    /**
     * 删除标签
     * @param id
     */
    public void remove(String id){
        articleLableDAO.remove(id);
    }


    public void articleLable(String articleId, List<Integer> lableId){

        if(lableId.size()>0){
            articleLableRelationDAO.removeByCriteria("article_id='"+articleId+"'");
            lableId.forEach(id->{
                ArticleLableRelationDTO dto = new ArticleLableRelationDTO();
                dto.setArticleId(articleId);
                dto.setLableId(id);
                articleLableRelationDAO.create(dto);
            });
        }

    }

}

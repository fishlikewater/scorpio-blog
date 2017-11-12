package scorpio.scorpioblog.mBlog.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.annotation.Tranctional;
import scorpio.scorpioblog.mBlog.dao.ArticleTypeDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleTypeDTO;

import java.util.List;

@Service
@Slf4j
public class ArticleTypeService {

    @Autowired
    private ArticleTypeDAO articleTypeDAO;

    /**
     * 编辑分类
     * @param dto
     */
    public String edit(ArticleTypeDTO dto){
        String id = "";
        if(StringUtils.isBlank(dto.getId())){
            String name = dto.getName();
            List list = articleTypeDAO.queryByCriteria("and name='" + name + "'");
            if(list.size()>0){
                log.error("存在该分类");
                return null;
            }
            id = articleTypeDAO.createAndId(dto);
        }else {
            id = dto.getId();
            articleTypeDAO.update(dto);
        }
        return id;
    }

    /**
     * 删除分类
     * @param id
     */
    public void remove(String id){
        articleTypeDAO.remove(id);
    }

}

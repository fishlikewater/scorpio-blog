package scorpio.scorpioblog.mBlog.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.annotation.Tranctional;
import scorpio.scorpioblog.mBlog.dao.ArticleLableDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableDTO;

import java.util.List;

@Service
@Slf4j
public class ArticleLableService {

    @Autowired
    private ArticleLableDAO articleLableDAO;

    /**
     * 标签编辑
     * @param dto
     */
    public String edit(ArticleLableDTO dto){

        String id = "";
        if(StringUtils.isBlank(dto.getId())){
            List list = articleLableDAO.queryByCriteria("and name='" + dto.getName() + "'");
            if(list.size()>0){
                log.error("存在该标签");
                return null;
            }
            id = articleLableDAO.createAndId(dto);
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


}

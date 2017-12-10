package scorpio.scorpioblog.mBlog.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import scorpio.BaseUtils;
import scorpio.annotation.Tranctional;
import scorpio.scorpioblog.mBlog.BlogConstant;
import scorpio.scorpioblog.mBlog.dao.ArticleDAO;
import scorpio.scorpioblog.mBlog.dao.ArticleDetailDAO;
import scorpio.scorpioblog.mBlog.dao.ArticleLableDAO;
import scorpio.scorpioblog.mBlog.dto.ArticleDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleDetailDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleLableDTO;
import scorpio.scorpioblog.utils.SystemUtils;
import scorpio.utils.UUIDUtils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleService {

    @Autowired
    private ArticleDetailDAO articleDetailDAO;
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private ArticleLableService articleLableService;
    @Autowired
    private ArticleLableDAO articleLableDAO;

    /**
     * 编辑文章
     * @param dto
     */
    @Tranctional(Connection.TRANSACTION_READ_UNCOMMITTED)
    public void edit(ArticleDTO dto, String content, String lables){

        /** 标签判断 */
        List<Integer> lableIdList = new ArrayList<>();
        if(StringUtils.isNotBlank(lables)){
            String[] tags = lables.split("\\,");
            for (String tag:tags){
                List<ArticleLableDTO> list = articleLableDAO.queryByCriteria("name = '" + tag + "'");
                if(list.size()>0){
                    lableIdList.add(list.get(0).getId());
                }else{
                    ArticleLableDTO dto1 = new ArticleLableDTO();
                    dto1.setName(tag);
                    lableIdList.add(articleLableService.edit(dto1));
                }
            }
            dto.setLable(lableIdList);
        }

        if(StringUtils.isBlank(dto.getaId())){
            ArticleDetailDTO ddto = new ArticleDetailDTO();
            ddto.setId(UUIDUtils.get());
            ddto.setContent(content);
            dto.setaId(UUIDUtils.get());
            dto.setContentId(ddto.getId());
            if(StringUtils.isBlank(dto.getShortContent())){
                dto.setShortContent(dto.getName());
            }
            if(StringUtils.isBlank(dto.gettPic())){
                dto.settPic(gen_thumb(content));
            }
            articleDAO.create(dto);
            articleDetailDAO.create(ddto);
        }else {
            ArticleDetailDTO ddto = new ArticleDetailDTO();
            ddto.setId(dto.getContentId());
            ddto.setContent(content);
            if(StringUtils.isBlank(dto.getShortContent())){
                dto.setShortContent(dto.getName());
            }
            if(StringUtils.isBlank(dto.gettPic())){
                dto.settPic(gen_thumb(content));
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setUpdateTime(sf.format(System.currentTimeMillis()));
            articleDAO.update(dto);
            articleDetailDAO.update(ddto);
        }
        articleLableService.articleLable(dto.getaId(), dto.getLable());
    }


    /**
     * 没有设置图片时的处理
     * @param content
     * @return
     */
    public String gen_thumb(String content){
        String getfirst = SystemUtils.get_first_thumb(content);
        if(StringUtils.isBlank(getfirst)){
            int cid = new Random().nextInt(20);
            int size = cid % 20;
            size = size == 0 ? 1 : size;
            return "/ui/pages/img/rand/" + size + ".jpg";
        }else {
            return getfirst;
        }
    }

    public void  test(){
        String path = this.getClass().getResource("/static/images/randimage").getPath();
        File file = new File(path);
        String[] list = file.list();
        for (int i = list.length - 1; i >= 0; i--) {
            System.out.println(list[i]);

        }
    }

    public static void main(String[] args) {
        ArticleService a = new ArticleService();
        a.test();
    }

    public int isPublic(String id, Boolean status) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        paramMap.put("status",status==true?1:0);
        return articleDAO.executeUpdate("updateStatus", paramMap);

    }
}

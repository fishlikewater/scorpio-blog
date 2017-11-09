package scorpio.scorpioblog.mBlog.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import scorpio.scorpioblog.mBlog.dto.ArticleDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleDetailDTO;
import scorpio.scorpioblog.mBlog.service.ArticleService;
import scorpio.utils.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class AdminController {

    @Autowired
    private ArticleDTO articleDTO;
    @Autowired
    private ArticleDetailDTO articleDetailDTO;
    @Autowired
    private ArticleService articleService;
    /**
     *
     * 登录
     * @param mv
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mv, HttpServletRequest request) {
        mv.addObject("random", new Random().nextInt(5)+1+".png");
        mv.addObject("baseUrl",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
        mv.setViewName("admin/login");
        return mv;
    }

    /**
     * 编辑文章
     * @param dto
     */
    public void edit(ArticleDTO dto, String content){
        articleService.edit(dto,content);
    }

    public void remove(String id){
        articleDTO.delete(id);
        articleDetailDTO.delete(id);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(5));

        }
    }
}

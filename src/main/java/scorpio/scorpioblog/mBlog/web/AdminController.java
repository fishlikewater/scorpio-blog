package scorpio.scorpioblog.mBlog.web;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import scorpio.core.BaseObject;
import scorpio.core.BasicObject;
import scorpio.pageUtil.Page;
import scorpio.pageUtil.PageRow;
import scorpio.scorpioblog.mBlog.dao.ArticleDAO;
import scorpio.scorpioblog.mBlog.dao.ArticleLableDAO;
import scorpio.scorpioblog.mBlog.dao.ArticleTypeDAO;
import scorpio.scorpioblog.mBlog.dto.*;
import scorpio.scorpioblog.mBlog.service.ArticleLableService;
import scorpio.scorpioblog.mBlog.service.ArticleService;
import scorpio.scorpioblog.mBlog.service.ArticleTypeService;
import scorpio.utils.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class AdminController {

    @Autowired
    private ArticleDTO articleDTO;
    @Autowired
    private ArticleDetailDTO articleDetailDTO;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private ArticleTypeDAO articleTypeDAO;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleLableService articleLableService;
    @Autowired
    private ArticleLableDAO articleLableDAO;
    /**
     *
     * 登录
     * @param mv
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mv, HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals("zhangx")) {
            mv.setViewName("redirect:admin/index");
            return mv;
        }
        mv.addObject("random", new Random().nextInt(5)+1+".png");
        mv.setViewName("admin/login");
        return mv;
    }

    /**
     * 进入后台框架页
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/admin/index")
    public ModelAndView mainView(ModelAndView mv,  HttpServletRequest request){
        mv.setViewName("admin/index");
        return mv;
    }

    /**
     * 框架默认打开页
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/admin/main")
    public ModelAndView firstPage(ModelAndView mv,  HttpServletRequest request){
        mv.setViewName("admin/main");
        return mv;
    }

    /**
     * 文章列表页
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/admin/list")
    public ModelAndView list(ModelAndView mv,  HttpServletRequest request){
        mv.setViewName("admin/list");
        return mv;
    }


    /**
     * 文章列表数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/admin/list/articles")
    public JSONObject getArticles(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit){
        String sql = "select a.a_id id,a.content_id as contentId, a.name,a.author,a.is_groom as isGroom,c.name typeName,b.name lableName,a.update_time updateTime,a.scrq from m_article a left join article_lable b on a.lable = b.id " +
                "left join article_type c on a.type_id=c.id";
        List list = articleDAO.queryByTpl(sql, (page - 1) * limit, limit);
        long count = articleDTO.count();
        JSONObject obj = new JSONObject();
        obj.put("data",list);
        obj.put("count",count);
        obj.put("code",0);
        obj.put("msg","");
        return obj;

    }


    /**
     * 进入分类设置页面
     * @param mv
     * @return
     */
    @GetMapping("/admin/type")
    public ModelAndView typePage(ModelAndView mv){
        mv.setViewName("admin/type");
        return mv;
    }

    /**
     * 加载分类数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("admin/type/list")
    public JSONObject getTypeData(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit){
        String sql = "select * from article_type order by order_by";
        List list = articleTypeDAO.queryByTpl(sql, limit * (page - 1), limit);
        Integer count = articleTypeDAO.queryCount();
        JSONObject obj = new JSONObject();
        obj.put("data",list);
        obj.put("count",count);
        obj.put("code",0);
        obj.put("msg","");
        return obj;
    }

    /**
     * 添加分类
     * @param dto
     */
    @PostMapping("admin/type/add")
    public String addType(ArticleTypeDTO dto){
        return articleTypeService.edit(dto);
    }

    /**
     * 删除分类
     * @param id
     */
    @PostMapping("admin/type/delete")
    public void delete(String id){
        articleTypeService.remove(id);
    }


    /**
     * 进入标签设置页面
     * @param mv
     * @return
     */
    @GetMapping("/admin/lable")
    public ModelAndView lablePage(ModelAndView mv){
        mv.setViewName("admin/lable");
        return mv;
    }

    /**
     * 加载标签数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("admin/lable/list")
    public JSONObject getLableData(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit){
        String sql = "select * from article_lable";
        List list = articleLableDAO.queryByTpl(sql, limit * (page - 1), limit);
        Integer count = articleTypeDAO.queryCount();
        JSONObject obj = new JSONObject();
        obj.put("data",list);
        obj.put("count",count);
        obj.put("code",0);
        obj.put("msg","");
        return obj;
    }


    /**
     * 添加标签
     * @param dto
     */
    @PostMapping("admin/lable/add")
    public String addLable(ArticleLableDTO dto){
        return articleLableService.edit(dto);
    }

    /**
     * 删除标签
     * @param id
     */
    @PostMapping("admin/lable/delete")
    public void deleteLable(String id){
        articleLableService.remove(id);
    }


    /**
     * 进入文章编辑页面
     * @param mv
     * @return
     */
    @GetMapping("/admin/blog/edit")
    public ModelAndView editPage(ModelAndView mv, @RequestParam(value = "id",required = false) String id){
        if(StringUtils.isNotBlank(id)){
            //编辑
            ArticlesDTO dto = (ArticlesDTO) articleDAO.findById(id);
            mv.addObject("dto", dto);
            ArticleDetailDTO detailDTO = articleDetailDTO.findById(dto.getContentId());
            mv.addObject("detailDTO", detailDTO);
        }
        mv.setViewName("admin/edit");
        return mv;
    }



    /**
     * 编辑文章
     * @param dto
     */
    @PostMapping("admin/blog/add")
    public String edit(ArticleDTO dto, String content){
        articleService.edit(dto,content);
        return "ok";
    }

    @PostMapping("admin/blog/detele")
    public void remove(String id, String cId){
        articleDTO.delete(id);
        articleDetailDTO.delete(cId);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(5));

        }
    }
}

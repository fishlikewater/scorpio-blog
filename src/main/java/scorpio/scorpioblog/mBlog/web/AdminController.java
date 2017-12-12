package scorpio.scorpioblog.mBlog.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import scorpio.core.BaseObject;
import scorpio.pageUtil.Page;
import scorpio.pageUtil.PageRow;
import scorpio.scorpioblog.mBlog.dao.*;
import scorpio.scorpioblog.mBlog.dto.*;
import scorpio.scorpioblog.mBlog.service.ArticleLableService;
import scorpio.scorpioblog.mBlog.service.ArticleService;
import scorpio.scorpioblog.mBlog.service.ArticleTypeService;
import scorpio.utils.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
public class AdminController {

    @Autowired
    private ArticleDetailDAO articleDetailDAO;
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
    @Autowired
    private ArticleLableRelationDAO articleLableRelationDAO;

    /**
     * 登录
     *
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
        mv.addObject("random", new Random().nextInt(5) + 1 + ".png");
        mv.setViewName("admin/login");
        return mv;
    }

    /**
     * 进入后台框架页
     *
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping({"/admin/index", "/admin"})
    public ModelAndView mainView(ModelAndView mv, HttpServletRequest request) {
        mv.setViewName("admin/index");
        return mv;
    }

    /**
     * 框架默认打开页
     *
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/admin/main")
    public ModelAndView firstPage(ModelAndView mv, HttpServletRequest request) {
        mv.setViewName("admin/main");
        return mv;
    }

    /**
     * 文章列表页
     *
     * @param mv
     * @param request
     * @return
     */
    @RequestMapping("/admin/list")
    public ModelAndView list(ModelAndView mv, HttpServletRequest request) {
        mv.setViewName("admin/list");
        return mv;
    }


    /**
     * 文章列表数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/admin/list/articles")
    public JSONObject getArticles(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit) {
        String sql = "select a.a_id id,a.content_id as contentId,a.is_public, a.name,a.author,a.is_groom as isGroom,c.name typeName,a.update_time updateTime,a.scrq from m_article a " +
                "left join article_type c on a.type_id=c.id";
        List list = articleDAO.queryByTpl(sql, (page - 1) * limit, limit);
        long count = articleDAO.queryCount();
        JSONObject obj = new JSONObject();
        obj.put("data", list);
        obj.put("count", count);
        obj.put("code", 0);
        obj.put("msg", "");
        return obj;

    }


    /**
     * 进入分类设置页面
     *
     * @param mv
     * @return
     */
    @GetMapping("/admin/type")
    public ModelAndView typePage(ModelAndView mv) {
        mv.setViewName("admin/type");
        return mv;
    }

    /**
     * 加载分类数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("admin/type/list")
    public JSONObject getTypeData(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit) {
        String sql = "select * from article_type order by order_by";
        List list = articleTypeDAO.queryByTpl(sql, limit * (page - 1), limit);
        Integer count = articleTypeDAO.queryCount();
        JSONObject obj = new JSONObject();
        obj.put("data", list);
        obj.put("count", count);
        obj.put("code", 0);
        obj.put("msg", "");
        return obj;
    }

    /**
     * 添加分类
     *
     * @param dto
     */
    @PostMapping("admin/type/add")
    public String addType(ArticleTypeDTO dto) {
        return articleTypeService.edit(dto);
    }

    /**
     * 删除分类
     *
     * @param id
     */
    @PostMapping("admin/type/delete")
    public void delete(String id) {
        articleTypeService.remove(id);
    }


    /**
     * 进入标签设置页面
     *
     * @param mv
     * @return
     */
    @GetMapping("/admin/lable")
    public ModelAndView lablePage(ModelAndView mv) {
        mv.setViewName("admin/lable");
        return mv;
    }

    /**
     * 加载标签数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("admin/lable/list")
    public JSONObject getLableData(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "30") Integer limit) {
        String sql = "select * from article_lable";
        List list = articleLableDAO.queryByTpl(sql, limit * (page - 1), limit);
        Integer count = articleTypeDAO.queryCount();
        JSONObject obj = new JSONObject();
        obj.put("data", list);
        obj.put("count", count);
        obj.put("code", 0);
        obj.put("msg", "");
        return obj;
    }


    /**
     * 添加标签
     *
     * @param dto
     */
    @PostMapping("admin/lable/add")
    public Integer addLable(ArticleLableDTO dto) {
        return articleLableService.edit(dto);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    @PostMapping("admin/lable/delete")
    public void deleteLable(String id) {
        articleLableService.remove(id);
    }


    /**
     * 进入文章编辑页面
     *
     * @param mv
     * @return
     */
    @GetMapping("/admin/blog/edit")
    public ModelAndView editPage(ModelAndView mv, @RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            //编辑
            ArticlesDTO dto = (ArticlesDTO) articleDAO.findById(id);
            mv.addObject("dto", dto);
            ArticleDetailDTO detailDTO = (ArticleDetailDTO) articleDetailDAO.findById(dto.getContentId());
            mv.addObject("detailDTO", detailDTO);
        }
        mv.setViewName("admin/edit");
        return mv;
    }


    /**
     * 编辑文章
     *
     * @param dto
     */
    @PostMapping("admin/blog/add")
    public String edit(ArticleDTO dto, String content, String lables) {
        articleService.edit(dto, content, lables);
        return "ok";
    }


    /**
     * 编辑文章
     *
     * @param id
     */
    @GetMapping("admin/blog/update")
    public ModelAndView edits(String id, ModelAndView mv) {
        ArticleDTO dto = (ArticleDTO) articleDAO.findById(id);
        ArticleDetailDTO detail = (ArticleDetailDTO) articleDetailDAO.findById(dto.getContentId());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        List<String> list = articleLableRelationDAO.queryForListByTpl("queryLable",paramMap, String.class);
        mv.addObject("lable", StringUtils.join(list, ","));
        mv.addObject("dto", dto);
        mv.addObject("detail", detail);
        mv.setViewName("admin/update");
        return mv;
    }

    @RequestMapping("admin/blog/content")
    public JSONObject content(@RequestParam("id") String id, HttpServletRequest request) {
        ArticleDetailDTO detail = (ArticleDetailDTO) articleDetailDAO.findById(id);
        return (JSONObject) JSON.toJSON(detail);
    }

    /**
     * 文章删除
     *
     * @param id
     * @param cId
     */
    @PostMapping("admin/blog/detele")
    public void remove(String id, String cId) {
        articleDAO.remove(id);
        articleDetailDAO.remove(cId);
        articleLableRelationDAO.removeByCriteria("article_id='" + id + "'");
    }

    /**
     * 是否发布
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("admin/blog/public")
    public JSONObject isPublic(String id, Boolean status) {
        Map<String, Object> retunMap = new HashMap<>();
        if (StringUtils.isBlank(id) || status == null) {
            retunMap.put("stattus", 001);
            return (JSONObject) JSON.toJSON(retunMap);
        }
        int count = articleService.isPublic(id, status);
        if (count == 0) {
            retunMap.put("stattus", 001);
            return (JSONObject) JSON.toJSON(retunMap);
        }
        retunMap.put("stattus", 000);
        return (JSONObject) JSON.toJSON(retunMap);
    }

    /**
     * 文件管理
     *
     * @param mv
     * @return
     */
    @GetMapping("admin/blog/file")
    public ModelAndView blogFile(ModelAndView mv) {
        mv.setViewName("/admin/upload");
        return mv;
    }

    /**
     * 日程
     *
     * @param mv
     * @return
     */
    @GetMapping("admin/agenda")
    public ModelAndView agenda(ModelAndView mv) {
        mv.setViewName("/agenda/index");
        return mv;
    }



   /* @PostMapping("/admin/upload")
    public void upload(@RequestParam("file")MultipartFile file){
        System.out.println(file.getOriginalFilename());
    }*/


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(5));

        }
    }
}

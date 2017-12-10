package scorpio.scorpioblog.mBlog.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import scorpio.core.BaseObject;
import scorpio.scorpioblog.mBlog.dao.*;
import scorpio.scorpioblog.mBlog.dto.*;
import scorpio.scorpioblog.mBlog.model.Archive;
import scorpio.scorpioblog.utils.AjaxResult;
import scorpio.scorpioblog.utils.SystemUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class IndexController {

    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private ArticleDetailDAO articleDetailDAO;
    @Autowired
    private ArticleLableDAO articleLableDAO;
    @Autowired
    private ArticleLableRelationDAO articleLableRelationDAO;

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    /**
     * 进入首页
     * @param mv
     * @return
     */
    @RequestMapping(value = {"/index","/"})
    public ModelAndView index(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                              @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit,
                              ModelAndView mv, HttpServletRequest request){

        /** 按时间获取文章列表*/
        List<Map<String, Object>> allArticleList = articleDAO.queryByTpl("query",limit*(page-1),limit);
        Integer count = articleDAO.queryCountByTpl("queryCount", null);

        if(allArticleList != null){
            mv.addObject("articles",allArticleList);
        }
        mv.addObject("page",page);
        mv.addObject("totals",Math.ceil(count/limit));
        /** 添加基础路径*/
        mv.setViewName("pages/index");
        return mv;
    }

    /**
     * 进入链接页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/links")
    public ModelAndView links(ModelAndView mv){
        mv.setViewName("pages/links");
        return mv;
    }

    /**
     * 进入About页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/about")
    public ModelAndView about(ModelAndView mv){
        mv.setViewName("pages/about");
        return mv;
    }

    /**
     * 进入archives页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/archives")
    public ModelAndView archives(ModelAndView mv){
        List<Map<String, Object>> allArticleList = articleDAO.queryByTpl("queryAll");
        List<Archive> list = new ArrayList<>();
        Map<String, Archive> cachemap = new LinkedHashMap<>();
        List<String> dateList = new ArrayList<>();
        for (Map<String, Object> map : allArticleList) {
            String scrq = map.get("scrq") + "";
            scrq = scrq.substring(0,7).replaceAll("-",".");
            if(dateList.contains(scrq)){
                cachemap.get(scrq).getContents().add(map);
            }else{
                dateList.add(scrq);
                List<Map<String, Object>> artList = new ArrayList<>();
                Archive archive = new Archive();
                artList.add(map);
                archive.setDatestr(scrq);
                archive.setContents(artList);
                cachemap.put(scrq, archive);
            }

        }
        cachemap.forEach((k, v)->{list.add(v);});
        mv.addObject("archives", list);
        mv.setViewName("pages/archives");
        return mv;
    }


    /**
     * 获取文章详情
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/article/{aId}")
    public ModelAndView goDetail(@PathVariable(name = "aId") String aId,
                                 ModelAndView mv){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", aId);
        List<Map<String, Object>> list = articleDAO.queryByTpl("queryById", paramMap);
        List<String> tagList = new ArrayList<>();
        if(list.size()>0){
            String content = list.get(0).get("content") + "";
            list.get(0).put("content", SystemUtils.markdownToHtml(content));
        }
        tagList = articleLableRelationDAO.queryForListByTpl("queryLable", paramMap, String.class);
        mv.addObject("tags",tagList);
        mv.addObject("article",list.get(0));
        mv.setViewName("pages/detail");
        return mv;
    }

    /**
     * 底部最新文章
     * @return
     */

    @RequestMapping(value = "/index/re_articles",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getRecentArticles(ModelMap map){
        return new AjaxResult(getRecentArticles());
    }

    /**
     * 获取最近的文章
     */
    public AjaxResult getRecentArticles(){
        List<ArticleDTO> list = articleDAO.queryByCriteria("1=1 order by scrq desc limit 6");
        return new AjaxResult(list);
    }


    /**
     * 点击标签搜索
     * @param tag
     * @return
     */
    @GetMapping("index/seacherTag/{tag}")
    public ModelAndView seacherByTag(@PathVariable("tag") String tag,
                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit", defaultValue = "12") Integer limit,
                                     ModelAndView mv){
        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(tag)){
            paramMap.put("tag", tag);
        }
        int being = (page-1)*limit;
        List list = articleDAO.queryByTpl("queryByTag",paramMap,being,limit);
        int count = articleDAO.queryCountByTpl("queryCountByTag", paramMap);
        mv.addObject("keywords",tag);
        mv.addObject("totals",Math.ceil(count/limit));
        mv.addObject("page",page+1);
        mv.addObject("type","标签");
        mv.addObject("pageflag","tag");
        mv.addObject("icons",ICONS);
        mv.addObject("clists",list);
        mv.setViewName("/pages/pageCategory");
        return mv;

    }

    /**
     * 点击分类搜索
     * @param typeId
     * @return
     */
    @GetMapping("index/seacherType/{typeId}")
    public ModelAndView seacherByType(@PathVariable("typeId") String typeId,
                                     @RequestParam(value = "page",defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit", defaultValue = "12") Integer limit,
                                     ModelAndView mv){
        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(typeId)){
            paramMap.put("typeId", typeId);
        }
        int being = (page-1)*limit;
        List<Map<String, Object>> list = articleDAO.queryByTpl("queryByKeyWord",paramMap,being,limit);
        int count = articleDAO.queryCountByTpl("queryCountByKeyWord", paramMap);

        if(list != null && list.size()>0){
            String type = list.get(0).get("type")+"";
            mv.addObject("keywords",type);

        }
        mv.addObject("totals",Math.ceil(count/limit));
        mv.addObject("page",page+1);
        mv.addObject("type","分类");
        mv.addObject("pageflag","tag");
        mv.addObject("clists",list);
        mv.setViewName("pages/pageCategory");
        return mv;

    }

    /**
     * 标题搜索
     * @param title
     * @return
     */
    @GetMapping("index/seacher/{title}")
    public ModelAndView seacher(@PathVariable("title") String title,
                                      @RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "12") Integer limit,
                                      ModelAndView mv){
        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(title)){
            paramMap.put("title", title);
        }
        int being = (page-1)*limit;
        List<Map<String, Object>> list = articleDAO.queryByTpl("queryByTitle",paramMap,being,limit);
        int count = articleDAO.queryCountByTpl("queryCountByTitle", paramMap);
        mv.addObject("keywords",title);
        mv.addObject("totals",Math.ceil(count/limit));
        mv.addObject("page",page+1);
        mv.addObject("type","标题");
        mv.addObject("pageflag","tag");
        mv.addObject("clists",list);
        mv.setViewName("pages/pageCategory");
        return mv;

    }

    /**
     * 进入标签页
     * @param mv
     * @return
     */
    @GetMapping("index/lable")
    public ModelAndView goLable(ModelAndView mv){
        List query = articleLableDAO.query();
        mv.addObject("list", query);
        mv.setViewName("pages/lable");
        return mv;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("2017-05-21".substring(0,7));
    }

}

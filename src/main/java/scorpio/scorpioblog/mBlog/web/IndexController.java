package scorpio.scorpioblog.mBlog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import scorpio.core.BaseObject;
import scorpio.scorpioblog.mBlog.dto.ArticleDTO;
import scorpio.scorpioblog.mBlog.dto.ArticleDetailDTO;
import scorpio.scorpioblog.mBlog.dto.TitleDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class IndexController {

    @Autowired
    private TitleDTO tDto;
    @Autowired
    private ArticleDTO articleDTO;
    @Autowired
    private ArticleDetailDTO articleDetailDTO;
    /**
     * 进入首页
     * @param mv
     * @return
     */
    @RequestMapping(value = {"/index","/"})
    public ModelAndView index(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                              @RequestParam(value = "limit",required = false,defaultValue = "10")Integer limit,
                              ModelAndView mv, HttpServletRequest request){
       /* *//** 获取板块 *//*
        List<TitleDTO> list = tDto.findAll();
        mv.addObject("titleList", list);*/
        /** 按时间获取文章列表*/
        List<BaseObject> allArticleList = articleDTO.page(page,limit,"scrq desc").getRows();
        if(allArticleList != null){
            mv.addObject("allArticleList",allArticleList);
        }
        /** 获取推荐文章，从推荐列表中随机获取3个*/
        List<BaseObject> groomArtcleList = articleDTO.where("is_groom", true).page(1, 3).getRows();
        if(groomArtcleList != null){
            mv.addObject("groomArticle",groomArtcleList);
        }
        /** 添加基础路径*/
        mv.setViewName("index");
        mv.addObject("baseUrl",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
        return mv;
    }

    /**
     * 进入About页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/about")
    public ModelAndView about(ModelAndView mv){
        mv.setViewName("about");
        return mv;
    }

    /**
     * 进入生活页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/life")
    public ModelAndView life(ModelAndView mv){
        mv.setViewName("life");
        return mv;
    }

    /**
     * 进入学习页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/learn")
    public ModelAndView learn(ModelAndView mv){
        mv.setViewName("learn");
        return mv;
    }

    /**
     * 进入留言页面
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/gbook")
    public ModelAndView gbook(ModelAndView mv){
        mv.setViewName("gbook");
        return mv;
    }

    /**
     * 获取文章详情
     * @param id
     * @param mv
     * @return
     */
    @RequestMapping(value = "index/detail/{aId}/{contentId}")
    public ModelAndView goDetail(@PathVariable(name = "contentId") String id,
                                 @PathVariable(name = "aId") String aId,
                                 ModelAndView mv){
        ArticleDTO aDTO = articleDTO.findById(aId); //基本信息
        ArticleDetailDTO detailDTO = articleDetailDTO.findById(id);//内容详情
        mv.addObject("article",aDTO);
        mv.addObject("detailDTO",detailDTO);
        mv.setViewName("detail");
        return mv;
    }
}

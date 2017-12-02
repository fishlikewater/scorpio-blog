package scorpio.scorpioblog.mBlog.model;

import scorpio.scorpioblog.mBlog.dto.ArticleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangx on 2017/4/14.
 */
public class Archive {
    private String datestr;
    private Integer count;
    private List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

    public String getDatestr() {
        return datestr;
    }

    public void setDatestr(String datestr) {
        this.datestr = datestr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Map<String, Object>> getContents() {
        return contents;
    }

    public void setContents(List<Map<String, Object>> contents) {
        this.contents = contents;
    }
}

package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.annotation.Transient;
import scorpio.core.BaseObject;


@Data
public class ArticleDTO extends BaseObject{

    private String aId;

    private String name;

    private String author;

    private String contentId;

    private Boolean isGroom;

    private String tPic;

    private String updateTime;

    private String scrq;

    private String typeId;

    private String lable;

    private String shortContent;

    private Boolean isComment;

    @Transient
    private String day;
    @Transient
    private String year;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String gettPic() {
        return tPic;
    }

    public void settPic(String tPic) {
        this.tPic = tPic;
    }
}

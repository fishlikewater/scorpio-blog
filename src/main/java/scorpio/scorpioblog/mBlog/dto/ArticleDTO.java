package scorpio.scorpioblog.mBlog.dto;

import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.annotation.Transient;
import scorpio.core.BaseObject;


@Table(pojo = ArticleDTO.class,table = "m_article", pk = "a_id")
@Component
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
    @Transient
    private String lableName;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Boolean getGroom() {
        return isGroom;
    }

    public void setGroom(Boolean groom) {
        isGroom = groom;
    }

    public String gettPic() {
        return tPic;
    }

    public void settPic(String tPic) {
        this.tPic = tPic;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getScrq() {
        return scrq;
    }

    public void setScrq(String scrq) {
        String[] split = scrq.split(" ")[0].split("-");
        this.day = split[1]+"-"+split[2];
        this.year = split[0];
        this.scrq = scrq;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public Boolean getComment() {
        return isComment;
    }

    public void setComment(Boolean comment) {
        isComment = comment;
    }
}

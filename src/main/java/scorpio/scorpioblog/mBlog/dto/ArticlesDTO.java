package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.annotation.Transient;
import scorpio.core.BaseObject;

import java.util.List;

@Data
public class ArticlesDTO extends BaseObject {

    private String aId;

    private String name;

    private String author;

    private String contentId;

    private Boolean isGroom;

    private String tPic;

    private String updateTime;

    private String scrq;

    private String typeId;

    @Transient
    private List<Object> lable;

    private String shortContent;

    private Boolean isComment;

    private Boolean isPublic;
}

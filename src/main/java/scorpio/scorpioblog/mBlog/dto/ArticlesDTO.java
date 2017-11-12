package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import scorpio.core.BasicObject;

@Data
public class ArticlesDTO extends BasicObject {

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
}

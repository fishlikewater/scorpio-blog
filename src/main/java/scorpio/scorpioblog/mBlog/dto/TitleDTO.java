package scorpio.scorpioblog.mBlog.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import scorpio.annotation.Table;
import scorpio.core.BaseObject;

@Data
public class TitleDTO extends BaseObject{

    private String tId;

    private String tName;

    private Boolean isUse;

    private Integer order;

    private String address;

    private String en;

    private String update;

    private String scrq;
}

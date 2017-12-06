package scorpio.scorpioblog.agenda.dto;

import lombok.Data;

/**
 * Created by zhanx on 2017/12/4.
 */
@Data
public class Event {

    private String id;
    private String title;
    private String start;
    private String end;
    private String color;
    private String url;//链接
    private Boolean editable;//是否编辑
    private String backgroundColor;//背景颜色
    private String borderColor;//边框颜色。
    private String textColor;//文本颜色。
    private Boolean allDay;
    private String className;
}

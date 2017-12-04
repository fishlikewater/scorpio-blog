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
    private String allDay;
    private String className;
}

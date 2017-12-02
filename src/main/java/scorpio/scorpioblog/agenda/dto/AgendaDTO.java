package scorpio.scorpioblog.agenda.dto;

import lombok.Data;
import scorpio.core.BaseObject;
@Data
public class AgendaDTO extends BaseObject {

    private String id;

    private String day;

    private String beginTime;

    private String endTime;

    private String content;

    private String updateTime;

    private String scrq;
}

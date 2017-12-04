package scorpio.scorpioblog.agenda.dto;

import lombok.Data;
import scorpio.core.BaseObject;
@Data
public class AgendaDTO extends BaseObject {

    private static final long serialVersionUID = 1L;
    private String id;//ID
    private String title;//日程内容
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String allDay;//是否全天，1 - 是，0 - 不是
    private String color;//颜色
    private String userID;//用户ID
    private String isFinish;//是否完成，1 - 是，0 - 不是
    private String createTime;//创建时间

    /**
     * CREATE TABLE `Schedule` (
     `id` varchar(32) NOT NULL COMMENT 'ID',
     `title` varchar(100) DEFAULT NULL COMMENT '日程内容',
     `startTime` datetime DEFAULT NULL COMMENT '开始时间',
     `endTime` datetime DEFAULT NULL COMMENT '结束时间',
     `allDay` varchar(2) DEFAULT NULL COMMENT '是否全天，1 - 是，0 - 不是',
     `color` varchar(20) DEFAULT NULL COMMENT '颜色',
     `userID` varchar(32) DEFAULT NULL COMMENT '用户ID',
     `isFinish` varchar(2) DEFAULT '0' COMMENT '是否完成，1 - 是，0 - 不是',
     `createTime` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
     */
}

package scorpio.scorpioblog.agenda.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scorpio.core.BaseObject;
import scorpio.scorpioblog.agenda.dao.AgendaDAO;
import scorpio.scorpioblog.agenda.dto.AgendaDTO;
import scorpio.scorpioblog.agenda.dto.Event;
import scorpio.scorpioblog.agenda.service.AgendaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AgendaController {

    @Autowired
    private AgendaDAO agendaDAO;
    @Autowired
    private AgendaService agendaService;

    /**
     * 加载日程
     * @param beginDay
     * @param endDay
     * @return
     */
    @GetMapping("/admin/agenda/list")
    public Object getAgenda(@RequestParam(value = "beginDay", required = true)String beginDay,
                                @RequestParam(value = "endDay", required = true) String endDay){
        String begin = StringEscapeUtils.escapeSql(beginDay);
        String end = StringEscapeUtils.escapeSql(endDay);
        Map paramMap = new HashMap();
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        List<Event> list = agendaDAO.queryForListByMap("query", paramMap, Event.class);
        if(list.size() == 0){
            return null;
        }
        return JSON.toJSON(list);
    }


    /**
     * 创建日程
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/admin/agenda/create")
    public JSONObject createAgenda(@RequestParam(value = "startTime",required = true) String startTime,
                                   @RequestParam(value = "endTime", required = true) String endTime,
                                   @RequestParam(value = "content", required = true)String content,
                                   @RequestParam(value = "name", required = true)String name,
                                   @RequestParam(value = "id", required = false)String id){

        Map<String, Object> map = new HashMap<>();
        AgendaDTO dto = new AgendaDTO();
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)){
            map.put("code",500);
            map.put("msg","error");
            return (JSONObject) JSON.toJSON(map);
        }
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setTitle(content);
        if(StringUtils.equals("month", name)){
            dto.setAllDay(true);
            dto.setClassName("allDay");
        }else{
            dto.setAllDay(false);
        }
        dto.setId(id);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(principal.getUsername());
        String Id = agendaService.edit(dto);
        map.put("id",Id);
        map.put("code",200);
        map.put("msg","success");

        return (JSONObject) JSON.toJSON(map);
    }


    /**
     * 根据id获取日程
     * @param id
     * @return
     */
    @GetMapping("/admin/agenda/{id}")
    public JSONObject getAgendaById(@PathVariable("id") String id){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(id)){
            map.put("code",500);
            map.put("msg","获取id失败");
            return (JSONObject) JSON.toJSON(map);
        }
        AgendaDTO dto = (AgendaDTO) agendaDAO.findById(id);
        map.put("code",200);
        map.put("data",dto);
        return (JSONObject) JSON.toJSON(map);
    }

    /**
     * 根据id删除日程
     * @param id
     * @return
     */
    @PostMapping("/admin/agenda/delete")
    public JSONObject delete(@RequestParam("id") String id){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(id)){
            map.put("code",500);
            map.put("msg","获取id失败");
            return (JSONObject) JSON.toJSON(map);
        }
        agendaService.delete(id);
        map.put("code",200);
        return (JSONObject) JSON.toJSON(map);
    }

    @PostMapping("admin/agenda/update")
    public JSONObject update(AgendaDTO dto){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(dto.getId())){
            map.put("code",500);
            map.put("msg","获取id失败");
            return (JSONObject) JSON.toJSON(map);
        }
        agendaService.update(dto);
        map.put("code",200);
        return (JSONObject) JSON.toJSON(map);
    }

}

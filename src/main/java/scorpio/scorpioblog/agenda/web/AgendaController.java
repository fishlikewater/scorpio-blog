package scorpio.scorpioblog.agenda.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JSONObject getAgenda(@RequestParam(value = "beginDay", required = true)String beginDay,
                                @RequestParam(value = "endDay", required = true) String endDay){
        String begin = StringEscapeUtils.escapeSql(beginDay);
        String end = StringEscapeUtils.escapeSql(endDay);
        Map paramMap = new HashMap();
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        List<Event> list = agendaDAO.queryForListByMap("query", paramMap, Event.class);
        return (JSONObject) JSON.toJSON(list);
    }


    /**
     * 创建日程
     * @param dto
     * @return
     */
    @PostMapping("/admin/agenda/create")
    public JSONObject createAgenda(AgendaDTO dto){
        String id = agendaService.edit(dto);
        Map<String, Object> map = new HashMap<>();
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

}

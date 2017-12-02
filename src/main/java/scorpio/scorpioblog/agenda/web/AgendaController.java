package scorpio.scorpioblog.agenda.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scorpio.scorpioblog.agenda.dao.AgendaDAO;
import scorpio.scorpioblog.agenda.dto.AgendaDTO;
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

    @GetMapping("/admin/agenda/list")
    public JSONObject getAgenda(@RequestParam(value = "beginDate", required = true)String beginDate,
                                @RequestParam(value = "endDate", required = true) String endDate){
        String begin = beginDate.replaceAll("/","-");
        String end = endDate.replaceAll("/","-");
        Map paramMap = new HashMap();
        paramMap.put("begin", begin);
        paramMap.put("end", end);
        List list = agendaDAO.queryByTpl("query", paramMap);

        return (JSONObject) JSON.toJSON(list);
    }

    /**
     * 编辑日程
     * @param dto
     */
    @PostMapping("/admin/agenda/add")
    public void addAgenda(AgendaDTO dto){
        agendaService.edit(dto);

    }

}

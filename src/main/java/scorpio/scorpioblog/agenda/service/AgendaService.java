package scorpio.scorpioblog.agenda.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.scorpioblog.agenda.dao.AgendaDAO;
import scorpio.scorpioblog.agenda.dto.AgendaDTO;
import scorpio.scorpioblog.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AgendaService {

    @Autowired
    private AgendaDAO agendaDAO;

    /**
     * 编辑
     * @param dto
     * @return
     */
    public String edit(AgendaDTO dto){

        String id = dto.getId();
        if(StringUtils.isBlank(id)){
            id = agendaDAO.createAndId(dto);
        }else{
            dto.setUpdateTime(DateUtil.formatDateALL(new Date()));
            agendaDAO.update(dto);
        }
        return id;
    }


    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        agendaDAO.remove(id);
    }


}

package scorpio.scorpioblog.agenda.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorpio.annotation.Tranctional;
import scorpio.scorpioblog.agenda.dao.AgendaDAO;
import scorpio.scorpioblog.agenda.dto.AgendaDTO;


@Service
@Tranctional(level = 1)
public class AgendaService {

    @Autowired
    private AgendaDAO agendaDAO;

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        agendaDAO.remove(id);
    }


    public String edit(AgendaDTO dto) {
        String id = dto.getId();
        if(StringUtils.isBlank(id)){
            id =  agendaDAO.createAndId(dto);
        }else{
            agendaDAO.update(dto);
        }

        return id;
    }

    /**
     * 拖拽更新
     * @param dto
     */
    public void update(AgendaDTO dto) {
        agendaDAO.updateIgnoreEmpty(dto);

    }
}

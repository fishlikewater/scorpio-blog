package scorpio.scorpioblog.agenda.dao;

import scorpio.annotation.Table;
import scorpio.core.BaseDAO;
import scorpio.scorpioblog.agenda.dto.AgendaDTO;

@Table(pojo = AgendaDTO.class, table = "agenda")
public class AgendaDAO extends BaseDAO{
}

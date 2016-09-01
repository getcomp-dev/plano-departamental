package hibernate;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Horario;
/*
 * gerencia as buscas no banco de dados para a classe/tabela horarios
 */
public class HorarioDAO extends GenericoDAO{
	public HorarioDAO(){
		
	}
	
	@SuppressWarnings("finally")
	public Horario getDataID(Date data) throws HibernateException, Exception{
		Horario horario = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Horario.class)
					.add(Restrictions.eq("dia", data));
			horario = (Horario) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			if(horario != null)
				return horario;
			
			horario  = new Horario(data);
			salvar(horario);
			return horario;
		}		
	}
}

package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Sala;
/*
 * gerencia as buscas no banco de dados para a classe/tabela sala
 */
public class SalaDAO extends GenericoDAO{

	public SalaDAO() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("finally")
	public static Sala encontraSalaPorNumero(String numeroSala) throws HibernateException, Exception {
		Sala sala = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Sala.class)
					.add(Restrictions.eq("numero", numeroSala));	
			sala = (Sala) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return sala;
		}
	}
	
	@SuppressWarnings({ "unchecked", "finally" })
	public List<Sala> procuraTodos() throws Exception {
		List<Sala> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Sala.class);	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}

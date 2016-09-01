package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Calouros;
/*
 * gerencia as buscas no banco de dados para a classe/tabela calouros
 */
public class CalourosDAO extends GenericoDAO {

	public CalourosDAO(){
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("finally")
	public Calouros encontraCalouroPorVagas(int numVagas) throws HibernateException, Exception {
		Calouros calouros = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Calouros.class)
					.add(Restrictions.eq("numVagas", numVagas));	
			calouros = (Calouros) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return calouros;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<Calouros> procuraTodos() throws Exception {
		List<Calouros> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Calouros.class);	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}

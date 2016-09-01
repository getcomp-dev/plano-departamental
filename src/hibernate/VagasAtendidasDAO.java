package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import timetable.VagasAtendidas;
/*
 * gerencia as buscas no banco de dados para a classe/tabela vagasAtendidas
 */
public class VagasAtendidasDAO extends GenericoDAO{
	
	public VagasAtendidasDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<VagasAtendidas> procuraTodos() throws Exception {
		List<VagasAtendidas> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(VagasAtendidas.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}

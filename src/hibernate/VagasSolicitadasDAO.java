package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import timetable.VagasSolicitadas;
/*
 * gerencia as buscas no banco de dados para a classe/tabela vagasSolicitadas
 */
public class VagasSolicitadasDAO extends GenericoDAO{
	
	public VagasSolicitadasDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<VagasSolicitadas> procuraTodos() throws Exception {
		List<VagasSolicitadas> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(VagasSolicitadas.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
}

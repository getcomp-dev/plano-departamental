package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.PedidosCoordenadores;
/*
 * gerencia as buscas no banco de dados para a classe/tabela pedidoCoordenadores
 */
public class PedidosCoordenadoresDAO extends GenericoDAO{

	public PedidosCoordenadoresDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<PedidosCoordenadores> procuraTodos() throws Exception {
		List<PedidosCoordenadores> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(PedidosCoordenadores.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<PedidosCoordenadores> procuraPedidosCoordenadoresPorAnoSemestre(int ano, int semestre){
		List<PedidosCoordenadores> pedidosCoordenadores = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(PedidosCoordenadores.class)
					.add(Restrictions.eq("ano", ano))
					.add(Restrictions.eq("semestre", semestre));	
			pedidosCoordenadores = (List<PedidosCoordenadores>) criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			try {
				getSession().close();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pedidosCoordenadores;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public boolean existePedidosCoordenadoresPorAnoSemestre(int ano, int semestre){
		List<PedidosCoordenadores> pedidosCoordenadores = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(PedidosCoordenadores.class)
					.add(Restrictions.eq("ano", ano))
					.add(Restrictions.eq("semestre", semestre));	
			pedidosCoordenadores = (List<PedidosCoordenadores>) criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			try {
				getSession().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pedidosCoordenadores != null && pedidosCoordenadores.size() > 0)
				return true;
			return false;
		}
	}		
}

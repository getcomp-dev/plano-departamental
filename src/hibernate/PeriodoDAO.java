package hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Disciplina;
/*
 * gerencia as buscas no banco de dados para a classe/tabela periodo
 */
public class PeriodoDAO extends GenericoDAO {
	public PeriodoDAO(){
		
	}
	
	@SuppressWarnings({ "finally" })
	public Boolean procuraPorNomeDisc(int idPeriodo, String nomeDisc) throws HibernateException, Exception{
		Disciplina disc = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Disciplina.class)
					.add(Restrictions.eq("nome", nomeDisc))
					.createAlias("periodo", "p")			
					.add(Restrictions.eq("p.id", idPeriodo));
			disc = (Disciplina) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			if(disc != null)
				return true;
			return false;
		}
	}
}

package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Turma;
import hibernate.GenericoDAO;
/*
 * gerencia as buscas no banco de dados para a classe/tabela turma
 */
public class TurmaDAO extends GenericoDAO{

	public TurmaDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "finally", "unchecked" })
	public List<timetable.Turma> procuraTodos() throws HibernateException, Exception {
		List<timetable.Turma> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Turma.class);		
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<timetable.Turma> encontraTurmasSemProf() throws HibernateException, Exception {
		List<timetable.Turma> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.isEmpty("docente"));	
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings("finally")
	public Turma encontraTurmasPorCodigo(String disciplinaCode, String turmaCode) throws HibernateException, Exception{
		Turma turma = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.eq("codigo", turmaCode))
					.createAlias("disciplina", "d")					
					.add(Restrictions.eq("d.codigo", disciplinaCode));
			turma = (Turma) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return turma;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<Turma> encontraTurmasPorCodigoDisc(String disciplinaCode) throws HibernateException, Exception{
		List<Turma> turma = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.createAlias("disciplina", "d")			
					.add(Restrictions.eq("d.codigo", disciplinaCode));
			turma = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return turma;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<Turma> procuraTurmasPorAnoSemestre(int ano, int semestre) throws HibernateException, Exception{
		List<Turma> turma = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.eq("ano", ano))
					.add(Restrictions.eq("semestre", semestre));
			turma = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return turma;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public int encontraMaxVagasPorCodigoDisc(String disciplinaCode) throws HibernateException, Exception{
		List<Turma> turma = null;
		try {			
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.createAlias("disciplina", "d")					
					.add(Restrictions.eq("d.codigo", disciplinaCode));
			turma = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			int totalVagas = 0;
			for(Turma t: turma)
				totalVagas += t.getMaxVagas();
			return totalVagas;
		}
	}
}

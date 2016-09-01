package hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import timetable.Docente;
import timetable.Turma;
/*
 * gerencia as buscas no banco de dados para a classe/tabela docente
 */
public class DocenteDAO extends GenericoDAO{

	public DocenteDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("finally")
	public Docente encontraDocentePorName(String nome) throws HibernateException, Exception {
		Docente docente = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Docente.class)
					.add(Restrictions.eq("nomeCompleto", nome));
			docente = (Docente) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return docente;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public List<Docente> procuraTodos() throws Exception {
		List<Docente> lista = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Docente.class);
			lista = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			return lista;
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public int somatorioCreditosPorCodigo(String codigo, int ano, int semestre) throws HibernateException, Exception {
		List<Turma> turma = null;
		try {
			Criteria criteria = getSession()
					.createCriteria(Turma.class)
					.add(Restrictions.eq("semestre", semestre))
					.add(Restrictions.eq("ano", ano))
					.createAlias("docente", "d")	
					.add(Restrictions.eq("d.codigo", codigo));
			turma = criteria.list();
		} catch (HibernateException e) {
			System.err.println(e.fillInStackTrace());
		} finally {
			getSession().close();
			int creditos = 0;
			for(Turma t: turma){
				creditos += t.getDisciplina().getCreditos();
			}
			return creditos;
		}
	}

}
